package ru.sberbank.homework.dragonblog.repository;

import org.springframework.stereotype.Repository;
import ru.sberbank.homework.dragonblog.model.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mart
 * 30.06.2019
 **/
@Repository
public class JpaPostRepository implements PostRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Post save(Post post, long userId) {
        return null;
    }

    @Override
    public boolean delete(long id, long userId) {
        return false;
    }

    @Override
    public Post get(long id) {
        return null;
    }

    @Override
    public List<Post> getAllByUser(long userId) {
        return null;
    }

    @Override
    public List<Post> getAllBySearchString(String search) {
        return null;
    }

    @Override
    public List<Post> getAllByDate(LocalDateTime dateTime) {
        return null;
    }
}
