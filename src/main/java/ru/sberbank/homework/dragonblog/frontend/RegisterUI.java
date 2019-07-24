package ru.sberbank.homework.dragonblog.frontend;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.HasValue;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.model.Gender;
import ru.sberbank.homework.dragonblog.model.Role;
import ru.sberbank.homework.dragonblog.model.User;
import ru.sberbank.homework.dragonblog.service.UserServiceImpl;


/**
 * Created by Mart
 * 22.07.2019
 **/
@SpringUI(path = "/register")
@Theme("metro")
@Title("Register")
public class RegisterUI extends UI {

    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    private VerticalLayout rootLayout;

    private GridLayout grid;

    private TextField usernameField;
    private TextField firstnameField;
    private TextField surnameField;

    private PasswordField firstPasswordField;
    private PasswordField secondPasswordField;

    private Label alreadyUsedName;
    private Label passwordStrength;
    private Label mismatchPassword;
    private Label nameValidLabel;
    private Label nickNameRuleInfo;
    private Label nickNameRuleInfo2;

    private Button register;

    private RadioButtonGroup sex;

    private boolean passwordValid;
    private boolean nicknameValid;
    private boolean nameValid;


    @Override
    protected void init(VaadinRequest request) {
        initView();
        setContent(rootLayout);
        setSizeFull();
    }

    private void initView() {
        initFields();
        initLayouts();
        setEvents();
    }

    private void initFields() {
        usernameField  = new TextField("Nickname");
        firstPasswordField = new PasswordField("Введите пароль");
        secondPasswordField = new PasswordField("Повторите пароль");
        register = new Button("Регистрация");
        alreadyUsedName = new Label();
        passwordStrength = new Label();
        mismatchPassword = new Label();
        firstnameField = new TextField("Имя");
        surnameField = new TextField("Фамилия");
        nameValidLabel = new Label("2-20 eng/рус символов");
        nickNameRuleInfo = new Label("nickname будет сохранен в нижнем регистре,");
        nickNameRuleInfo2 = new Label("доступны только eng/рус буквы и смиволы '-' и '@'");

        sex = new RadioButtonGroup<String>("Пол");

        sex.setItems("мужской", "женский");

        nameValidLabel.setVisible(false);
        usernameField.setMaxLength(20);
        firstPasswordField.setMaxLength(16);
        secondPasswordField.setMaxLength(16);
        firstnameField.setMaxLength(20);
        surnameField.setMaxLength(20);
    }

    private void setEvents() {
        setNickNameEvents();
        setPasswordFieldsEvents();
        setNameEvents();
    }

    private void setPasswordFieldsEvents() {
        firstPasswordField.addValueChangeListener(passwordStrengthCheckListener());
        secondPasswordField.addValueChangeListener(passwordMatchesListener());
        register.addClickListener(registerListener());
    }

    private void setNameEvents() {
        firstnameField.addValueChangeListener(nameSurnameListener());
        surnameField.addValueChangeListener(nameSurnameListener());
    }

    private Button.ClickListener registerListener() {
        return event -> {
            if (isRegisterValid()){
                User user = new User();
                user.setNickname(usernameField.getValue().toLowerCase());
                user.setPassword(firstPasswordField.getValue());
                user.setFirstName(firstnameField.getValue());
                user.setSurname(surnameField.getValue());
                Gender gender = sex.getValue().equals("мужской") ? Gender.MALE : Gender.FEMALE;
                user.addRole(Role.USER);
                user.setGender(gender);
                userService.saveNewUser(user);
                UI.getCurrent().getPage().setLocation("/login");
            }
        };
    }

    /**
     *  Напоминание:
     *
     * (?=.*[0-9])- строка содержит хотя бы одно число
     * (?=.*[!@#$%^&*]) - строка содержит хотя бы один спецсимвол
     * (?=.*[a-z]) - строка содержит хотя бы одну латинскую букву в нижнем регистре
     * (?=.*[A-Z]) - строка содержит хотя бы одну латинскую букву в верхнем регистре
     * [0-9a-zA-Z!@#$%^&*]{6,} - строка состоит не менее, чем из 6 вышеупомянутых символов
     */
    private HasValue.ValueChangeListener<String> passwordStrengthCheckListener() {
        return  e -> {
                String password = firstPasswordField.getValue();
                passwordStrength.setVisible(true);

                passwordStrength.setStyleName("label-text-red");
                passwordStrength.setValue("Слабый");

                if (password.matches(
                        "(?=.*[0-9])(?=.*[a-zа-я])(?=.*[A-ZА-Я])[0-9a-zа-яA-ZА-Я]{6,}")) {

                    passwordStrength.setValue("средний");
                    passwordStrength.setStyleName("label-text-yellow");
                }

                if (password.matches(
                        "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-zа-я])(?=.*[A-ZА-Я])[0-9a-zа-яA-ZА-Я!@#$%^&*]{6,}")) {

                    passwordStrength.setValue("Надежный");
                    passwordStrength.setStyleName("label-text-green");
                }
        };
    }

    private HasValue.ValueChangeListener<String> nameSurnameListener() {
        return event -> {
            nameValidLabel.setVisible(true);
            nameValidLabel.setStyleName(ValoTheme.LABEL_FAILURE);
            String name = firstnameField.getValue();
            String surname = surnameField.getValue();

            if (name.matches("[a-zа-яA-ZА-Я]{2,}")
                    && surname.matches("[a-zа-яA-ZА-Я]{2,}")) {
                nameValidLabel.setVisible(false);
                nameValid = true;
            } else {
                nameValidLabel.setVisible(true);
                nameValid = false;
            }
        };
    }

    private HasValue.ValueChangeListener<String> passwordMatchesListener() {
        return event -> {
            mismatchPassword.setStyleName(ValoTheme.LABEL_FAILURE);
            mismatchPassword.setValue("пароли не совпадают");
            mismatchPassword.setVisible(true);
            passwordValid = false;

            if (firstPasswordField.getValue().equals(secondPasswordField.getValue())) {
                mismatchPassword.setValue("пароли совпадают");
                mismatchPassword.setStyleName(ValoTheme.LABEL_SUCCESS);
                passwordValid = true;
            }
        };
    }

    private void setNickNameEvents() {
        usernameField.addValueChangeListener(e -> {
           String username = usernameField.getValue().toLowerCase();
           alreadyUsedName.addStyleName(ValoTheme.LABEL_FAILURE);
            if (!username.matches("(?=.*[a-zа-яA-ZА-Я])[@a-zA-Zа-яА-Я0-9-]{3,20}$")) {
                alreadyUsedName.setValue("не менее 3 и хотя бы 1 буква");
                alreadyUsedName.setVisible(true);
                alreadyUsedName.setStyleName(ValoTheme.LABEL_FAILURE);
                nicknameValid = false;
            } else {
                UiUser usernameFromDb = userService.findByNickname(username);
                if (usernameFromDb == null || !usernameFromDb.getNickname().equals(username)) {
                    alreadyUsedName.setValue("nickname свободен");
                    alreadyUsedName.setStyleName(ValoTheme.LABEL_SUCCESS);
                    nicknameValid = true;
                } else {
                    alreadyUsedName.setValue("nickname занят");
                    nicknameValid = false;
                }
            }
        });
    }

    private void initLayouts() {
        rootLayout = new VerticalLayout();
        rootLayout.setSizeFull();


        grid = new GridLayout(3, 6);
        grid.setSpacing(true);
        grid.setSizeUndefined();
        grid.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

        grid.addComponent(usernameField, 1, 0);
        grid.addComponent(firstPasswordField, 1, 3);
        grid.addComponent(secondPasswordField, 1, 4);
        grid.addComponent(register, 1, 5);

        grid.addComponent(alreadyUsedName, 0, 0);
        grid.addComponent(nickNameRuleInfo, 0, 1);
        grid.addComponent(nickNameRuleInfo2, 0, 2);
        grid.addComponent(passwordStrength, 0, 3);
        grid.addComponent(mismatchPassword, 0, 4);

        grid.addComponent(sex, 2, 1);
        grid.addComponent(nameValidLabel, 2, 2);
        grid.addComponent(firstnameField, 2, 3);
        grid.addComponent(surnameField, 2, 4);

        rootLayout.addComponent(grid);
        rootLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
    }

    private boolean isRegisterValid() {
        return isNameValid() && isPasswordValid()
                && isNicknameValid() && sex.getValue()!= null;
    }
    private boolean isPasswordValid(){
        return passwordValid;
    }

    private boolean isNicknameValid() {
        return nicknameValid;
    }

    private boolean isNameValid() {
        return nameValid;
    }


}
