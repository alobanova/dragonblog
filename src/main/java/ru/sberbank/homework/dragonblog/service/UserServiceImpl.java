package ru.sberbank.homework.dragonblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ru.sberbank.homework.dragonblog.frontend.converter.UserConverter;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.model.User;
import ru.sberbank.homework.dragonblog.repository.UserRepository;
import ru.sberbank.homework.dragonblog.util.NotFoundException;

/**
 * Created by Mart 01.07.2019
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

    public UiUser findByNickname(String nickname) {
        if (nickname == null) {
            return null;
        }

        User user = repository.findByNickname(nickname);
        if (user != null) {
            return converter.convert(user);
        }

        return null;
    }

    public List<UiUser> findByPartOfNickname(String value) {

        if (value == null) {
            return Collections.emptyList();
        }
        List<UiUser> uiUsers = Collections.emptyList();

        List<User> users = repository.findByNicknameRegex("%" + value.toLowerCase() + "%");
        if (users != null) {
            uiUsers = users.stream().map(converter::convert).collect(Collectors.toList());
        }

        return uiUsers;
    }

    public UiUser update(long id, User user) throws NotFoundException {
        if (id != user.getId()) {
            return null;
        }
        repository.update(id, user.getFirstName(), user.getSurname(), user.getPatronymic(),
                user.getGender(), user.getCity(), user.getBirthDate(), user.getDescription(), user.getAvatar());

        return converter.convert(user);
    }

    public UiUser update(long id, UiUser uiUser) throws NotFoundException {
        User user = converter.convertBack(uiUser);

        return update(id, user);
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
