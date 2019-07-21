package ru.sberbank.homework.dragonblog.frontend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.model.Gender;
import ru.sberbank.homework.dragonblog.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class UserConverter implements Converter<User, UiUser> {

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd.MM.yyyy", Locale.getDefault());

    @Override
    public UiUser convert(User source) {
        String sex = source.getGender() == Gender.FEMALE ? "женский" : "мужской";

        return UiUser.builder()
                .id(source.getId())
                .nickname(source.getNickname())
                .firstName(source.getFirstName())
                .surname(source.getSurname())
                .patronymic(source.getPatronymic())
                .gender(sex)
                .birthDate(formatter.format(source.getBirthDate()))
                .city(source.getCity())
                .description(source.getDescription())
                .build();
    }

    public User convertBack(UiUser uiUser) {
        Gender gender = uiUser.getGender().equals("мужской") ? Gender.MALE : Gender.FEMALE;

        User user = new User();
        user.setId(uiUser.getId());
        user.setNickname(uiUser.getNickname());
        user.setFirstName(uiUser.getFirstName());
        user.setSurname(uiUser.getSurname());
        user.setPatronymic(uiUser.getPatronymic());
        user.setGender(gender);
        user.setBirthDate(LocalDate.parse(uiUser.getBirthDate(), formatter));
        user.setCity(uiUser.getCity());
        user.setDescription(uiUser.getDescription());

        return user;
    }

}
