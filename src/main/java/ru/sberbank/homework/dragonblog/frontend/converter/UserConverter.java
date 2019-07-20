package ru.sberbank.homework.dragonblog.frontend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.model.Gender;
import ru.sberbank.homework.dragonblog.model.User;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class UserConverter implements Converter<User, UiUser> {

    private final PostConverter postConverter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY", Locale.getDefault());

    public UserConverter(PostConverter postConverter) {
        this.postConverter = postConverter;
    }

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
                .posts(postConverter.convert(source.getPosts()))
                .build();
    }

}
