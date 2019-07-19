package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.io.File;

@SpringView(name = ProfileView.NAME)
public class ProfileView extends HorizontalLayout implements View {
    public static final String NAME = "profile";

    private SpringNavigator navigator;

    private final VerticalLayout imageLayout = new VerticalLayout();
    private final VerticalLayout infoLayout = new VerticalLayout();

    private final Panel image = new Panel("Image");
    private final Panel info = new Panel("Info");
    private final Panel posts = new Panel("Posts");

    private final String basePath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

    public ProfileView(SpringNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        initRoot();
        initImagePanel();
        initInfoPanel();
        initPostsPanel();
    }

    private void initRoot() {
        setSizeFull();
        setSpacing(false);
        setMargin(false);

        imageLayout.setSizeFull();
        infoLayout.setSizeFull();

        addComponents(imageLayout, infoLayout);
        setExpandRatio(imageLayout, 2);
        setExpandRatio(infoLayout, 5);
    }

    private void initImagePanel() {
        imageLayout.addComponent(image);

        FileResource resource = new FileResource(new File(basePath + ""));

    }

    private void initInfoPanel() {
        info.setSizeFull();
        infoLayout.addComponent(info);
    }

    private void initPostsPanel() {
        posts.setSizeFull();
        infoLayout.addComponent(posts);
    }
}
