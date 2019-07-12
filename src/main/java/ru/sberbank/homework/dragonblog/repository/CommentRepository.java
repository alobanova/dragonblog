package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.homework.dragonblog.model.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    boolean deleteByIdAndAuthorId(long id, long userId);

    List<Comment> findAllByPostId(long postId);
}
