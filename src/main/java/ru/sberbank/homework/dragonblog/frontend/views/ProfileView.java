package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import ru.sberbank.homework.dragonblog.frontend.model.UiPost;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.frontend.util.AvatarUtils;
import ru.sberbank.homework.dragonblog.frontend.util.PostPanel;
import ru.sberbank.homework.dragonblog.security.SecurityUtils;
import ru.sberbank.homework.dragonblog.service.PostServiceImpl;
import ru.sberbank.homework.dragonblog.service.UserServiceImpl;

@SpringView(name = ProfileView.NAME)
public class ProfileView extends HorizontalLayout implements View {
    public static final String NAME = "profile";

    private PostServiceImpl postService;

    private final VerticalLayout imageLayout = new VerticalLayout();
    private final VerticalLayout infoLayout = new VerticalLayout();

    private final Panel imagePanel = new Panel();
    private final FormLayout info = new FormLayout();
    private final FormLayout postsLayout = new FormLayout();

    private UiUser user;

    private final UserServiceImpl service;

    public ProfileView(UserServiceImpl service,
                       PostServiceImpl postService) {
        this.service = service;
        this.postService = postService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        initRoot();
        initCurrentUser();
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

    private void initCurrentUser() {
        Object user_id = VaadinSession.getCurrent().getAttribute("user_id");
        if (user_id == null) {
            user = service.get(SecurityUtils.getUser().getId());
        } else {
            user = service.get((long) user_id);
        }
    }

    private void initImagePanel() {

        Image avatar = AvatarUtils.imageFromByteArray(user.getAvatar());

        imagePanel.setPrimaryStyleName("image-panel");
        imagePanel.setContent(avatar);

        imageLayout.addComponent(imagePanel);
        imageLayout.setWidth(95, Unit.PERCENTAGE);
    }

    private void initInfoPanel() {
        info.setSizeFull();
        info.setMargin(false);

        FormLayout data = initData();
        TextArea about = initAbout();

        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(data, "Данные");
        tabSheet.addTab(about, "О себе");

        Label nickname = new Label("<strong>" + user.getNickname() + "</strong>", ContentMode.HTML);
        nickname.setStyleName("nickname");
        info.addComponent(nickname);
        info.addComponent(tabSheet);
        infoLayout.addComponent(info);
    }

    private FormLayout initData() {
        FormLayout data = new FormLayout();
        data.setSizeFull();
        data.setMargin(false);
        data.setSpacing(false);
        data.setStyleName("data-about");

        String patronymic = user.getPatronymic() != null ? user.getPatronymic() : "";
        String fio = String.format("%s %s %s", user.getSurname(), user.getFirstName(), patronymic);

        data.addComponent(new Label("<strong>ФИО: </strong>" + fio, ContentMode.HTML));
        if (!user.getBirthDate().equals("")) {
            data.addComponent(
                    new Label("<strong>День рождения: </strong>" + user.getBirthDate(), ContentMode.HTML));
        }
        data.addComponent(new Label("<strong>Пол: </strong>" + user.getGender(), ContentMode.HTML));
        if (!user.getCity().equals("")) {
            data.addComponent(new Label("<strong>Город: </strong>" + user.getCity(), ContentMode.HTML));
        }

        return data;
    }

    private TextArea initAbout() {
        TextArea about = new TextArea();
        about.setSizeFull();
        about.setReadOnly(true);
        about.setStyleName("v-textarea");
        about.setMaxLength(1000);
        about.setRows(9);
        about.setStyleName("data-about");

        String description = user.getDescription();
        about.setValue(description);

        return about;
    }

    private void initPostsPanel() {
        postsLayout.setSizeFull();
        infoLayout.addComponent(postsLayout);

        infoLayout.setExpandRatio(info, 3);
        infoLayout.setExpandRatio(postsLayout, 7);

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
            if (newDescription != null && !newDescription.isEmpty()) {
                textArea.setValue("");
                //Чтото тут с проверкой не так на автора.. вседа же тру будет;
                UiPost post = UiPost.builder()
                        .author(user)
                        .description(newDescription)
                        .postDateTime(LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("HH:mm:ss dd.MM.yyyy", Locale.getDefault())))
                        .build();

                post = postService.create(post, user.getId());
                postsLayout.addComponent(formPanelPost(post), 1);
            }
        });

        verticalLayout.addComponent(textArea);
        verticalLayout.addComponent(create);
        panel.setContent(verticalLayout);
        postsLayout.addComponent(panel);
    }
}
