package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.homework.dragonblog.frontend.converter.PostConverter;
import ru.sberbank.homework.dragonblog.frontend.model.UiPost;
import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Mart
 * 01.07.2019
 **/
@Service
@Transactional
public class PostServiceImpl {
    private PostRepository repository;
    private final PostConverter converter;

    @Autowired
    public PostServiceImpl(PostRepository repository, PostConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public UiPost get(long id) {
        Post post = repository.findById(id).orElse(null);

        if (post != null) {
            post.getComments().size();
            return converter.convert(post);
        }

        return null;
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public UiPost update(UiPost post, long userId) {
        if(post.getAuthor().getId() == userId) {
            Post postSave = repository.save(converter.convertBack(post));
            return converter.convert(postSave);
        }
        return null;
    }

    public UiPost create(UiPost post, long userId) {
        return update(post, userId);
    }

    public List<UiPost> getAllByUser(long userId) {
        List<Post> posts = repository.findAllByAuthorIdOrderByPostDateTimeDesc(userId).orElse(Collections.emptyList());

        return converter.convert(posts);
    }

    public List<UiPost> findFavouritePosts(long id) {
        List<Post> posts = repository.findFavouritePosts(id);
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
