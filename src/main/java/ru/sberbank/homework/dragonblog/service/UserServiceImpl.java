package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sberbank.homework.dragonblog.model.User;
import ru.sberbank.homework.dragonblog.repository.UserRepository;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.util.List;

/**
 * Created by Mart
 * 01.07.2019
 **/
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User get(int id) throws NotFoundException {
        return null;
    }

    @Override
    public boolean delete(int id) throws NotFoundException {
        return false;
    }

    @Override
    public User update(User user) throws NotFoundException {
        return null;
    }

    @Override
    public User create(User user) {
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
