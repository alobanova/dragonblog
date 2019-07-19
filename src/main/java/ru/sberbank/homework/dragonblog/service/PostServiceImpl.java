package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.repository.PostRepository;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mart
 * 01.07.2019
 **/
@Service
public class PostServiceImpl implements PostService {
    private  PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post get(long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(null);
    }

    @Override
    public boolean delete(long id, long userId) throws NotFoundException {
        Post post = get(id);

        if(post.getAuthor().getId() != userId) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
    @Override
    public Post update(Post post, long userId) throws NotFoundException {
        if(post.getAuthor().getId() == userId) {
            repository.save(post);
            return post;
        }
        return null;
    }

    @Override
    public Post create(Post post, long userId) {
        return update(post, userId);
    }

    @Override
    public List<Post> getAllByUser(long userId) {
        return repository.findAllByAuthorIdOrderByPostDateTime(userId).orElse(null);
    }

    @Override
    public List<Post> getAllBySearchString(String search) {
        return null;
    }

    @Override
    public List<Post> getAllByDate(LocalDateTime dateTime) {
        return null;
    }
}
