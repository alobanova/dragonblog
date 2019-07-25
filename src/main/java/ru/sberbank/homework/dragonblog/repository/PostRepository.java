package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.homework.dragonblog.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findAllByAuthorIdOrderByPostDateTimeDesc(long userId);

    List<Post> findAllByPostDateTimeOrderByPostDateTime(LocalDateTime dateTime);

    @Query(value = "SELECT * FROM POSTS WHERE ID IN "
            + "(SELECT FAVOURITE_POST_ID FROM USER_SUBSCRIPTIONS WHERE USER_ID =?1)", nativeQuery = true)
    List<Post> findFavouritePosts(long id);
}
