package ru.sberbank.homework.dragonblog.repository;

import ru.sberbank.homework.dragonblog.model.Post;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Возможно имеет смысл все репозитории сделать через Spring Data Repository
 * пока не помню детали
 * */
public interface PostRepository {
    // null if post do not belong to userId
    Post save(Post post, long userId);

    // false if comment do not belong to author
    boolean delete(long id, long userId);

    // null if not found
    Post get(long id);

    List<Post> getAllByUser(long userId);

    List<Post> getAllBySearchString(String search);

    List<Post> getAllByDate(LocalDateTime dateTime);
}
