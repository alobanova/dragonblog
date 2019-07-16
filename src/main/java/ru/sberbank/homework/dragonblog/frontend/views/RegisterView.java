package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = RegisterView.NAME)
public class RegisterView extends VerticalLayout implements View {
    public static final String NAME = "register";

    private SpringNavigator navigator;

    public RegisterView(SpringNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(new Label("REGISTER AND EDIT PAGE"));
    }

}
