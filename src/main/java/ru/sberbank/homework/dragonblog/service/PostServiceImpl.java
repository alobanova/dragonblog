package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.repository.PostRepository;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

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
    public Post get(int id) throws NotFoundException {
        return null;
    }

    @Override
    public boolean delete(int id, int userId) throws NotFoundException {
        return false;
    }

    @Override
    public Post update(Post post, int userId) throws NotFoundException {
        return null;
    }

    @Override
    public Post create(Post post, int userId) {
        return null;
    }

    @Override
    public List<Post> getAllByUser(int userId) {
        return null;
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
