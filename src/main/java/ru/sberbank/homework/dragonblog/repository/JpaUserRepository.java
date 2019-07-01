package ru.sberbank.homework.dragonblog.repository;

import org.springframework.stereotype.Repository;
import ru.sberbank.homework.dragonblog.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Mart
 * 30.06.2019
 **/
@Repository
public class JpaUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> getBySearchString(String search) {
        return null;
    }
}
