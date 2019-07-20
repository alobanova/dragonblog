package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.homework.dragonblog.frontend.converter.UserConverter;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.model.User;
import ru.sberbank.homework.dragonblog.repository.UserRepository;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

import java.util.List;

/**
 * Created by Mart
 * 01.07.2019
 **/
@Service
@Transactional
public class UserServiceImpl {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter converter;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, UserConverter converter) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
    }

    public UiUser get(long id) throws NotFoundException {
        User user = repository.findById(id);

        if (user != null) {
            user.getPosts().size();
            return converter.convert(user);
        }

        return null;
    }

    public boolean delete(long id) throws NotFoundException {
        return false;
    }

    public UiUser update(User user) throws NotFoundException {
        return null;
    }

    public UiUser create(User user) {
        return null;
    }

    public List<UiUser> getAll() {
        return null;
    }

    public List<UiUser> getBySearchString(String search) {
        return null;
    }
}
