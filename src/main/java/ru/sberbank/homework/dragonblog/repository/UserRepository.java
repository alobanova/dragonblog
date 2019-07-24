package ru.sberbank.homework.dragonblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import ru.sberbank.homework.dragonblog.model.Gender;
import ru.sberbank.homework.dragonblog.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    User findByNickname(String nickname);

    @Query(value = "select * from USERS WHERE nickname LIKE ?1", nativeQuery = true)
    List<User> findByNicknameRegex(String regex);

    @Modifying
    @Query("update User u set u.firstName = ?2, u.surname = ?3, u.patronymic = ?4,"
            + "u.gender = ?5, u.city = ?6, u.birthDate = ?7, u.description = ?8, u.avatar = ?9 where u.id = ?1")
    void update(long id, String firstName, String surname, String patronymic,
                Gender gender, String city, LocalDate date, String description, byte[] avatar);
}
