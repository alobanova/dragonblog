package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = SearchView.NAME)
public class SearchView extends VerticalLayout implements View {
    public static final String NAME = "search";

    private SpringNavigator navigator;

    public SearchView(SpringNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        addComponent(new Label("SEARCH PAGE"));
    }

}
