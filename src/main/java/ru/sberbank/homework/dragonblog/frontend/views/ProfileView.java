package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import ru.sberbank.homework.dragonblog.frontend.model.UiPost;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.frontend.util.PostPanel;
import ru.sberbank.homework.dragonblog.service.PostServiceImpl;
import ru.sberbank.homework.dragonblog.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@SpringView(name = ProfileView.NAME)
public class ProfileView extends HorizontalLayout implements View {
    public static final String NAME = "profile";

    private SpringNavigator navigator;

    private PostServiceImpl postService;

    private final VerticalLayout imageLayout = new VerticalLayout();
    private final VerticalLayout infoLayout = new VerticalLayout();

    private final Panel imagePanel = new Panel();
    private final FormLayout info = new FormLayout();
    private final FormLayout postsLayout = new FormLayout();

    private UiUser user;

    private Image avatar;

    private final UserServiceImpl service;

    public ProfileView(SpringNavigator navigator,
                       UserServiceImpl service,
                       PostServiceImpl postService) {
        this.navigator = navigator;
        this.service = service;
        this.postService = postService;
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
        infoLayout.setWidth(100, Unit.PERCENTAGE);
        infoLayout.setHeightUndefined();
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

        String fio = String.format("%s %s %s", user.getSurname(), user.getFirstName(), user.getPatronymic());

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
        postsLayout.setSizeFull();
        infoLayout.addComponent(postsLayout);

        infoLayout.setExpandRatio(info, 3);
        infoLayout.setExpandRatio(postsLayout, 7);

        user = service.get(2);

        displayCreatePostPanel();

        for (UiPost post : getListPosts()) {
            postsLayout.addComponent(formPanelPost(post));
        }
    }

    private List<UiPost> getListPosts() {
        return postService.getAllByUser(user.getId());
    }

    private Panel formPanelPost(UiPost post) {
        PostPanel postPanel = new PostPanel(postService);
        return postPanel.getPanelPost(post, user, postsLayout);
    }

    private void displayCreatePostPanel() {
        Panel panel = new Panel("Создание нового поста");
        panel.setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout();

        TextArea textArea = new TextArea();
        textArea.setPlaceholder("Начните писать ваш новый пост...");
        textArea.setSizeFull();

        Button create = new Button("Создать");

        create.addClickListener((Button.ClickListener) event2 -> {
            String newDescription = textArea.getValue();
            textArea.setValue("");
            //Чтото тут с проверкой не так на автора.. вседа же тру будет;
            UiPost post = UiPost.builder()
                    .author(user)
                    .description(newDescription)
                    .postDateTime(LocalDateTime.now().format(DateTimeFormatter
                            .ofPattern("HH:mm:ss dd.MM.yyyy", Locale.getDefault())))
                    .build();

            postService.create(post, user.getId());
            postsLayout.addComponent(formPanelPost(post), 1);
        });

        verticalLayout.addComponent(textArea);
        verticalLayout.addComponent(create);
        panel.setContent(verticalLayout);
        postsLayout.addComponent(panel);
    }
}
