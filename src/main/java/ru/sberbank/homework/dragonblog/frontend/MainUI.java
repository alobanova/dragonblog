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
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import org.springframework.beans.factory.annotation.Autowired;

import ru.sberbank.homework.dragonblog.frontend.util.ValoMenu;
import ru.sberbank.homework.dragonblog.frontend.views.ErrorView;
import ru.sberbank.homework.dragonblog.frontend.views.ProfileView;

import javax.servlet.annotation.WebServlet;

@SpringUI
@Theme("metro")
@Title("DragonBlog")
public class MainUI extends UI {
    @Autowired
    private SpringViewProvider viewProvider;
    @Autowired
    private SpringNavigator navigator;

    private final VerticalLayout topLevelLayout = new VerticalLayout();
    private final HorizontalLayout contentLayout = new HorizontalLayout();
    private final HorizontalSplitPanel contentPanel = new HorizontalSplitPanel();
    private final Panel page = new Panel();

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();
        initView();
    }

    private void initView() {
        topLevelLayout.setSizeFull();
        topLevelLayout.setMargin(false);
        topLevelLayout.setSpacing(false);
        setContent(topLevelLayout);

        navigator.init(this, page);
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
        logo.setStyleName("title");
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.TOP_LEFT);
        logoWrapper.addStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);
        return logoWrapper;
    }

    private void initContent() {
        page.setSizeFull();

        contentPanel.setSizeFull();
        contentPanel.setSplitPosition(15, Unit.PERCENTAGE);

        contentPanel.addComponent(new ValoMenu());
        contentPanel.addComponent(page);

        contentLayout.setSizeFull();
        contentLayout.addComponent(contentPanel);
        topLevelLayout.addComponent(contentLayout);
        topLevelLayout.setExpandRatio(contentLayout, 15);
    }

    @WebServlet(urlPatterns = {"/*", "/VAADIN/*"}, name = "AppServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class AppServlet extends SpringVaadinServlet {
    }

}
