package ru.sberbank.homework.dragonblog.service;

import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    Post get(int id) throws NotFoundException;

    // false if post do not belong to author
    boolean delete(int id, int userId) throws NotFoundException;

    // null if post do not belong to author
    Post update(Post post, int userId) throws NotFoundException;

    Post create(Post post, int userId);

    List<Post> getAllByUser(int userId);

    //will find in uniquename
    List<Post> getAllBySearchString(String search);

    List<Post> getAllByDate(LocalDateTime dateTime);
}
