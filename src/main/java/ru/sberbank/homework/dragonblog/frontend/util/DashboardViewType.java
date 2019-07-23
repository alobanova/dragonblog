package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

import ru.sberbank.homework.dragonblog.frontend.views.ProfileView;
import ru.sberbank.homework.dragonblog.frontend.views.EditView;
import ru.sberbank.homework.dragonblog.frontend.views.SearchView;

public enum DashboardViewType {
    PROFILE("profile", "Профиль", ProfileView.class, VaadinIcons.MALE, true),
    SEARCH("search", "Поиск", SearchView.class, VaadinIcons.SEARCH, false),
    EDIT("register", "Редактировать", EditView.class, VaadinIcons.EDIT, false),
    EXIT("exit", "Выход",null, VaadinIcons.SIGN_OUT, false);

    private final String viewName;
    private final String caption;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName, final String caption,
                              final Class<? extends View> viewClass, final Resource icon,
                              final boolean stateful) {
        this.viewName = viewName;
        this.caption = caption;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public String getCaption() {
        return caption;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}