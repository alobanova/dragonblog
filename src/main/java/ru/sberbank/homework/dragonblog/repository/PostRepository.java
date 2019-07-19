package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.homework.dragonblog.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Возможно имеет смысл все репозитории сделать через Spring Data Repository
 * пока не помню детали
 * */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findAllByAuthorIdOrderByPostDateTime(long userId);

    //List<Post> getAllBySearchString(String search);

    //List<Post> findAllByPostDateTime(LocalDateTime dateTime);

}
