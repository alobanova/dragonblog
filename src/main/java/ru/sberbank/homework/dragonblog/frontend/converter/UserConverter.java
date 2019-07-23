package ru.sberbank.homework.dragonblog.frontend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.model.Gender;
import ru.sberbank.homework.dragonblog.model.User;

@Component
public class UserConverter implements Converter<User, UiUser> {

    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd.MM.yyyy", Locale.ENGLISH);

    @Override
    public UiUser convert(User user) {
        String gender = user.getGender() == Gender.FEMALE ? "женский" : "мужской";
        String patronymic = user.getPatronymic() == null ? "" : user.getPatronymic();
        String city = user.getCity() == null ? "" : user.getCity();
        String description = user.getDescription() == null ? "" : user.getDescription();

        return UiUser.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .firstName(user.getFirstName())
                .surname(user.getSurname())
                .patronymic(patronymic)
                .gender(gender)
                .birthDate(convertDateToString(user.getBirthDate()))
                .city(city)
                .description(description)
                .avatar(user.getAvatar())
                .build();
    }

    public User convertBack(UiUser uiUser) {
        Gender gender = uiUser.getGender().equals("мужской") ? Gender.MALE : Gender.FEMALE;
        String patronymic = uiUser.getPatronymic().equals("") ? null : uiUser.getPatronymic();
        String city = uiUser.getCity().equals("") ? null : uiUser.getCity();
        String description = uiUser.getDescription().equals("") ? null : uiUser.getDescription();

        User user = new User();
        user.setId(uiUser.getId());
        user.setNickname(uiUser.getNickname());
        user.setFirstName(uiUser.getFirstName());
        user.setSurname(uiUser.getSurname());
        user.setPatronymic(patronymic);
        user.setGender(gender);
        user.setBirthDate(convertStringToDate(uiUser.getBirthDate()));
        user.setCity(city);
        user.setAvatar(uiUser.getAvatar());
        user.setDescription(description);

        return user;
    }

    public static LocalDate convertStringToDate(String date) {
        return date.equals("") ? null : LocalDate.parse(date, formatter);
    }

    public static String convertDateToString(LocalDate date) {
        return date == null ? "" : formatter.format(date);
    }
}
