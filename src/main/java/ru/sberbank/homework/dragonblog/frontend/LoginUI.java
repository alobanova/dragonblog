package ru.sberbank.homework.dragonblog.frontend;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.*;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;


/**
 * Created by Mart
 * 17.07.2019
 **/
@SpringUI(path = "/login")
@Theme("metro")
@Title("Login")
public class LoginUI extends UI {

    private VaadinSharedSecurity vaadinSecurity;

    @Autowired
    public void setVaadinSecurity(VaadinSharedSecurity vaadinSecurity) {
        this.vaadinSecurity = vaadinSecurity;
    }

    private final VerticalLayout loginLayout = new VerticalLayout();
    private final VerticalLayout rootLayout = new VerticalLayout();

    private TextField userNameField;
    private PasswordField passwordField;
    private Button loginBtn;
    private Button registerBtn;
    private CheckBox rememberMe;

    private Label loginFailedLabel;
    private Label loggedOutLabel;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Страница авторизации");
        initLayout();
        initFields();
        initLoginForm();

        Notification registeredNotif = new Notification("вы успешно зарегистрировались");
        registeredNotif.setPosition(Position.TOP_CENTER);
        registeredNotif.setDelayMsec(2000);



        if (VaadinSession.getCurrent().getAttribute("registered") != null) {
            registeredNotif.show(Page.getCurrent());
        }

        if (request.getParameter("logout") != null) {
            loggedOutLabel.setVisible(true);
        }

        setContent(rootLayout);
        setSizeFull();
    }

    private void initLayout() {
        loginLayout.setSpacing(true);
        loginLayout.setSizeUndefined();
        rootLayout.addComponent(loginLayout);
        rootLayout.setSizeFull();
        rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
    }

    private void initLoginForm() {
        FormLayout loginForm = new FormLayout();
        loginForm.setSizeUndefined();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        loginForm.addComponent(userNameField);
        loginForm.addComponent(passwordField);
        horizontalLayout.addComponent(loginBtn);
        horizontalLayout.addComponent(registerBtn);
        loginForm.addComponent(rememberMe);
        loginForm.addComponent(horizontalLayout);

        loggedOutLabel.addStyleName(ValoTheme.LABEL_SUCCESS);
        loggedOutLabel.setSizeUndefined();
        loginLayout.addComponent(loggedOutLabel);
        loginLayout.setComponentAlignment(loggedOutLabel, Alignment.BOTTOM_CENTER);

        loginLayout.addComponent(loginForm);
        loginLayout.setComponentAlignment(loginForm, Alignment.TOP_CENTER);
    }

    private void initFields() {
        userNameField = new TextField("Логин");
        passwordField = new PasswordField("Пароль");
        loginBtn = new Button("Войти");
        registerBtn = new Button("Регистрация");
        registerBtn.addClickListener(register());
        rememberMe = new CheckBox("запомнить меня");
        loggedOutLabel = new Label("возвращайтесь снова!");

        loggedOutLabel.setVisible(false);
        loginBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        loginBtn.setDisableOnClick(true);
        loginBtn.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginBtn.addClickListener(e -> login());

        loginLayout.addComponent(loginFailedLabel = new Label());
        loginLayout.setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER);
        loginFailedLabel.setSizeUndefined();
        loginFailedLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        loginFailedLabel.setVisible(false);
    }

    private void login() {
        try {
            vaadinSecurity.login(userNameField.getValue().toLowerCase(),
                    passwordField.getValue(),
                    rememberMe.getValue());

        } catch (AuthenticationException ex) {
            userNameField.focus();
            userNameField.selectAll();
            passwordField.setValue("");
            loginFailedLabel.setValue(String.format("Не удалось войти %s", ex.getMessage()));
            loginFailedLabel.setVisible(true);

            if (loggedOutLabel != null) {
                loggedOutLabel.setVisible(false);
            }
        } catch (Exception ex) {
            Notification.show("ошибка : ", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
        } finally {
            loginBtn.setEnabled(true);
        }
    }

    private Button.ClickListener register() {
        return (Button.ClickListener) event -> getPage().setLocation("/register");
    }
}





