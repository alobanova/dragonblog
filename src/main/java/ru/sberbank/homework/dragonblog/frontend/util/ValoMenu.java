package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;


public final class ValoMenu extends CssLayout {

    public ValoMenu() {

        addStyleName("valo-menuitems");

        for (final DashboardViewType view : DashboardViewType.values()) {
            if (view == DashboardViewType.EXIT) {
                addComponent(new Label());
            }
            addComponent(new ValoMenuItemButton(view));
        }
    }

    public final class ValoMenuItemButton extends Button {

        private final DashboardViewType view;

        ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setStyleName("menu-item");
            setIcon(view.getIcon());
            setCaption(view.getCaption().substring(0, 1).toUpperCase()
                    + view.getCaption().substring(1));

            addClickListener((ClickListener) event -> UI.getCurrent().getNavigator()
                    .navigateTo(view.getViewName()));
        }

    }
}