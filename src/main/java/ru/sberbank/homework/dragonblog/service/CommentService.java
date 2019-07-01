package ru.sberbank.homework.dragonblog.service;

import ru.sberbank.homework.dragonblog.model.Comment;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.util.List;

public interface CommentService {
    Comment update(Comment comment)  throws NotFoundException;

    boolean delete(int id, int userId) throws NotFoundException;

    Comment create(Comment comment);

    List<Comment> getAllByPost(int postId);
}
