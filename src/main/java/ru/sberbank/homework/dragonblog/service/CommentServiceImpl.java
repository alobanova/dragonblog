package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.homework.dragonblog.model.Comment;
import ru.sberbank.homework.dragonblog.repository.CommentRepository;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.util.List;

/**
 * Created by Mart
 * 01.07.2019
 **/
@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository repository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment update(Comment comment) throws NotFoundException {
        return null;
    }

    @Override
    public boolean delete(int id, int userId) throws NotFoundException {
        return false;
    }

    @Override
    public Comment create(Comment comment) {
        return null;
    }

    @Override
    public List<Comment> getAllByPost(int postId) {
        return null;
    }
}
