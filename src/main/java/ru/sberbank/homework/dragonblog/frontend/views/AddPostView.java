package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AddPostView.NAME)
public class AddPostView extends VerticalLayout implements View {
    public static final String NAME = "addPost";

    private SpringNavigator navigator;

    public AddPostView(SpringNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(new Label("ADD POST PAGE"));
    }
}
