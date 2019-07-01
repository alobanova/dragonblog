package ru.sberbank.homework.dragonblog.repository;

import ru.sberbank.homework.dragonblog.model.Comment;

import java.util.List;

/**
 * Возможно имеет смысл все репозитории сделать через Spring Data Repository
 * пока не помню детали
 * */
public interface CommentRepository {
    Comment save(Comment comment);

    // false if comment do not belong to author/ post author/ admin
    boolean delete(long id, long userId);

    List<Comment> getAllByPost(long postId);
}
