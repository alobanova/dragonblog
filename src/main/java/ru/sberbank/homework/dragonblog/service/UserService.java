package ru.sberbank.homework.dragonblog.service;

import ru.sberbank.homework.dragonblog.model.User;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.util.List;

public interface UserService {
    User get(int id)  throws NotFoundException;

    boolean delete(int id)  throws NotFoundException;

    User update(User user)  throws NotFoundException;

    User create(User user);

    List<User> getAll();

    //will find in firstName / surname / uniqueName
    List<User> getBySearchString(String search);
}
