package ru.sberbank.homework.dragonblog.frontend;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

/**
 * Created by Mart
 * 22.07.2019
 **/
@SpringUI(path = "/register")
@Theme("metro")
@Title("Register")
public class RegisterUI extends UI {

    @Autowired
    VaadinSharedSecurity vaadinSecurity;

    @Override
    protected void init(VaadinRequest request) {

    }
}
