package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import ru.sberbank.homework.dragonblog.model.Comment;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteByIdAndAuthorId(long id, long authorId);

    Optional<List<Comment>> findAllByPostIdOrderByDate(long id);

    @Modifying
    @Query("update Comment c set c.description = ?2 where c.id = ?1")
    void update(long id, String description);

    Comment findById(long id);
}
