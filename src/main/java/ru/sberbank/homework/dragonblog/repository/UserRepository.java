package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.model.User;

import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

}
