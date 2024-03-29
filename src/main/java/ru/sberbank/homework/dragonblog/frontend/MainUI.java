package ru.sberbank.homework.dragonblog.frontend;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.security.VaadinSecurity;
import ru.sberbank.homework.dragonblog.frontend.util.ValoMenu;
import ru.sberbank.homework.dragonblog.frontend.views.ErrorView;
import ru.sberbank.homework.dragonblog.frontend.views.ProfileView;

import javax.servlet.annotation.WebServlet;

@SpringUI
@Theme("metro")
@Title("DragonBlog")
public class MainUI extends UI {
    private final SpringViewProvider viewProvider;
    private final SpringNavigator navigator;

    @Autowired
    VaadinSecurity vaadinSecurity;

    private final VerticalLayout topLevelLayout = new VerticalLayout();
    private final HorizontalLayout contentLayout = new HorizontalLayout();
    private final HorizontalLayout pageLayout = new HorizontalLayout();

    private final HorizontalSplitPanel contentPanel = new HorizontalSplitPanel();

    public MainUI(
            final SpringViewProvider viewProvider, final SpringNavigator navigator) {
        this.viewProvider = viewProvider;
        this.navigator = navigator;
    }

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();
        initView();
    }

    private void initView() {
        topLevelLayout.setWidth(100, Unit.PERCENTAGE);
        topLevelLayout.setHeight("-1px");
        topLevelLayout.setMargin(false);
        topLevelLayout.setSpacing(false);
        setContent(topLevelLayout);

        navigator.init(this, pageLayout);
        navigator.setErrorView(ErrorView.class);
        navigator.addProvider(viewProvider);
        initTitle();
        initContent();

        if (navigator.getState() == null || navigator.getState().isEmpty()) {
            navigator.navigateTo(ProfileView.NAME);
        } else {
            navigator.navigateTo(navigator.getState());
        }

    }

    private void initTitle() {
        Component title = buildTitle();
        topLevelLayout.addComponents(title);
        topLevelLayout.setExpandRatio(title, 1);
    }

    private Component buildTitle() {
        Label logo = new Label("<strong>Dragon</strong>Blog",
                ContentMode.HTML);
        logo.setSizeFull();
        logo.setStyleName("title");

        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setPrimaryStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);

        return logoWrapper;
    }

    private void initContent() {
        pageLayout.setSizeFull();

        int width = (int) (getPage().getBrowserWindowWidth() / 1.5);

        contentPanel.setWidth(width, Unit.PIXELS);
        contentPanel.setSplitPosition(25, Unit.PERCENTAGE);
        contentPanel.setLocked(true);
        contentPanel.addComponents(new ValoMenu(), pageLayout);

        contentLayout.setSizeFull();
        contentLayout.setStyleName("no-margin");
        contentLayout.addComponent(contentPanel);
        contentLayout.setComponentAlignment(contentPanel, Alignment.MIDDLE_CENTER);

        topLevelLayout.addComponent(contentLayout);
        topLevelLayout.setExpandRatio(contentLayout, 19);
    }

    @WebServlet(urlPatterns = {"/*", "/VAADIN/*"}, name = "AppServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class AppServlet extends SpringVaadinServlet {
    }

}
