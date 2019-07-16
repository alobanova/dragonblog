package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.model.User;

import java.util.List;

/**
 * Возможно имеет смысл все репозитории сделать через Spring Data Repository
 * пока не помню детали
 * */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    User save(User user);
//
//    // false if not found
//    boolean delete(long id);
//
//    // null if not found
//    User get(long id);
//
//    List<User> getAll();
//
//    List<User> getBySearchString(String search);
}
