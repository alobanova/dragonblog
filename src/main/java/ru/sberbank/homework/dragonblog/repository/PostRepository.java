package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.homework.dragonblog.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findAllByAuthorIdOrderByPostDateTime(long userId);

    //List<Post> getAllBySearchString(String search);

    List<Post> findAllByPostDateTimeOrderByPostDateTime(LocalDateTime dateTime);

    void deleteByIdAndAuthorId(long id, long authorId);

}
