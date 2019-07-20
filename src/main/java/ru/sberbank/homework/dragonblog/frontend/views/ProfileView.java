package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.service.UserServiceImpl;

@SpringView(name = ProfileView.NAME)
public class ProfileView extends HorizontalLayout implements View {
    public static final String NAME = "profile";

    private SpringNavigator navigator;

    private final VerticalLayout imageLayout = new VerticalLayout();
    private final VerticalLayout infoLayout = new VerticalLayout();

    private final Panel imagePanel = new Panel();
    private final FormLayout info = new FormLayout();
    private final Panel posts = new Panel("Posts");

    private Image avatar;

    private final UserServiceImpl service;

    public ProfileView(SpringNavigator navigator, UserServiceImpl service) {
        this.navigator = navigator;
        this.service = service;
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
        imageLayout.setMargin(false);
        infoLayout.setSizeFull();
        infoLayout.setMargin(false);

        addComponents(imageLayout, infoLayout);
        setExpandRatio(imageLayout, 6);
        setExpandRatio(infoLayout, 14);
    }

    private void initImagePanel() {

        avatar = new Image("", new ThemeResource("../metro/img/avatar2.jpg"));
        avatar.setPrimaryStyleName("avatar");

        imagePanel.setPrimaryStyleName("image-panel");
        imagePanel.setContent(avatar);

        imageLayout.addComponent(imagePanel);
        imageLayout.setWidth(95, Unit.PERCENTAGE);

    }

    private void initInfoPanel() {
        info.setSizeFull();
        info.setMargin(false);

        UiUser user = service.get(2);

        FormLayout data = initData(user);
        TextArea about = initAbout(user);

        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(data, "Данные");
        tabSheet.addTab(about, "О себе");

        Label nickname = new Label("<strong>" + user.getNickname() + "</strong>", ContentMode.HTML);
        nickname.setStyleName("nickname");
        info.addComponent(nickname);
        info.addComponent(tabSheet);
        infoLayout.addComponent(info);
    }

    private FormLayout initData(UiUser user) {
        FormLayout data = new FormLayout();
        data.setSizeFull();
        data.setMargin(false);
        data.setSpacing(false);
        data.setStyleName("data-about");

        String fio = String.format("%s %s %s", user.getFirstName(), user.getSurname(), user.getPatronymic());

        data.addComponent(new Label("<strong>ФИО: </strong>" + fio, ContentMode.HTML));
        data.addComponent(new Label("<strong>День рождения: </strong>" + user.getBirthDate(), ContentMode.HTML));
        data.addComponent(new Label("<strong>Пол: </strong>" + user.getGender(), ContentMode.HTML));
        data.addComponent(new Label("<strong>Город: </strong>" + user.getCity(), ContentMode.HTML));

        return data;
    }

    private TextArea initAbout(UiUser user) {
        TextArea about = new TextArea();
        about.setSizeFull();
        about.setReadOnly(true);
        about.setStyleName("v-textarea");
        about.setMaxLength(1000);
        about.setRows(8);
        about.setStyleName("data-about");
        about.setValue(user.getDescription());

        return about;
    }

    private void initPostsPanel() {
        posts.setSizeFull();
        infoLayout.addComponent(posts);

        infoLayout.setExpandRatio(info, 3);
        infoLayout.setExpandRatio(posts, 7);
    }
}
