package ru.sberbank.homework.dragonblog.frontend;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.osgi.resources.OsgiVaadinTheme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.homework.dragonblog.frontend.views.ErrorView;
import ru.sberbank.homework.dragonblog.frontend.views.ProfileView;

import javax.servlet.annotation.WebServlet;

@SpringUI
@Theme("valo")
public class MainUI extends UI {
    @Autowired
    private SpringViewProvider viewProvider;
    @Autowired
    private SpringNavigator navigator;

    private final VerticalLayout topLevelLayout = new VerticalLayout();
    private final Panel content = new Panel();

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();
        initView();
    }

    private void initView() {
        topLevelLayout.addComponent(new Label("Tralala"));

        topLevelLayout.setSpacing(false);
        topLevelLayout.setSizeFull();
        setContent(topLevelLayout);

        navigator.init(this, content);
        navigator.setErrorView(ErrorView.class);
        navigator.addProvider(viewProvider);
        initMenu();
        initContent();

        if (navigator.getState() == null || navigator.getState().isEmpty()) {
            navigator.navigateTo(ProfileView.NAME);
        } else {
            navigator.navigateTo(navigator.getState());
        }

    }

    private void initMenu() {

    }

    private void initContent() {

    }

    @WebServlet(urlPatterns = {"/*", "/VAADIN/*"}, name = "AppServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class AppServlet extends SpringVaadinServlet {
    }

}
