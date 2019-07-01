package ru.sberbank.homework.dragonblog.repository;

import org.springframework.stereotype.Repository;
import ru.sberbank.homework.dragonblog.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Mart
 * 30.06.2019
 **/
@Repository
public class JpaCommentRepository implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public boolean delete(long id, long userId) {
        return false;
    }

    @Override
    public List<Comment> getAllByPost(long postId) {
        return null;
    }
}
