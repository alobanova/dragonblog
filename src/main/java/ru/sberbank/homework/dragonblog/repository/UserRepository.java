package ru.sberbank.homework.dragonblog.repository;

import ru.sberbank.homework.dragonblog.model.User;

import java.util.List;

/**
 * Возможно имеет смысл все репозитории сделать через Spring Data Repository
 * пока не помню детали
 * */
public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(long id);

    // null if not found
    User get(long id);

    List<User> getAll();

    List<User> getBySearchString(String search);
}
