package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.DateField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import lombok.Getter;
import ru.sberbank.homework.dragonblog.frontend.converter.UserConverter;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;

@Getter
public class EditInfoFields {
    private final RadioButtonGroup<String> gender = new RadioButtonGroup<>("Пол");
    private TextField surname;
    private TextField firstName;
    private TextField patronymic;
    private DateField birthday;
    private TextField city;
    private TextArea about;

    private final UiUser user;

    public EditInfoFields(final UiUser user) {
        this.user = user;

        init();
    }

    private void init() {
        gender.setItems("мужской", "женский");
        gender.setSelectedItem(user.getGender());

        surname = new TextField("Фамилия ", user.getSurname());
        surname.setRequiredIndicatorVisible(true);
        surname.setMaxLength(30);

        firstName = new TextField("Имя ", user.getFirstName());
        firstName.setRequiredIndicatorVisible(true);
        firstName.setMaxLength(30);

        patronymic = new TextField("Отчество ", user.getPatronymic());
        patronymic.setMaxLength(30);

        birthday = new DateField("День рождения ", UserConverter.convertStringToDate(user.getBirthDate()));
        city = new TextField("Город ", user.getCity());
        about = new TextArea("О себе ", user.getDescription());

        about.setWidth(80, Sizeable.Unit.PERCENTAGE);
        about.setMaxLength(1000);
        about.setRows(9);
    }
}
