package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.homework.dragonblog.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    boolean deleteByIdAndAuthorId(long id, long authorId);

    Optional<List<Comment>> findAllByPostId(long id);
}
