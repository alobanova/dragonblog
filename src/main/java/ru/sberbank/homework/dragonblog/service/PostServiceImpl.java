package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.homework.dragonblog.frontend.converter.PostConverter;
import ru.sberbank.homework.dragonblog.frontend.model.UiPost;
import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.repository.PostRepository;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mart
 * 01.07.2019
 **/
@Service
public class PostServiceImpl {
    private PostRepository repository;
    private final PostConverter converter;

    @Autowired
    public PostServiceImpl(PostRepository repository, PostConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public UiPost get(long id) throws NotFoundException {
        Post post = repository.findById(id).orElse(null);

        if (post != null) {
            return converter.convert(post);
        }

        return null;
    }

    public void delete(long id, long userId) throws NotFoundException {
        repository.deleteByIdAndAuthorId(id, userId);
    }

    public UiPost update(Post post, long userId) throws NotFoundException {
        if(post.getAuthor().getId() == userId) {
            repository.save(post);
            return converter.convert(post);
        }
        return null;
    }

    public UiPost create(Post post, long userId) {
        return update(post, userId);
    }

    public List<UiPost> getAllByUser(long userId) {
        List<Post> posts = repository.findAllByAuthorIdOrderByPostDateTime(userId).orElse(Collections.emptyList());

        return converter.convert(posts);
    }

    public List<UiPost> getAllBySearchString(String search) {
        return null;
    }

    public List<UiPost> getAllByDate(LocalDateTime dateTime) {
        List<Post> posts = repository.findAllByPostDateTimeOrderByPostDateTime(dateTime);

        return converter.convert(posts);
    }
}
