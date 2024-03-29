package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.vaadin.ui.themes.ValoTheme;
import ru.sberbank.homework.dragonblog.frontend.model.UiPost;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.frontend.util.DeleteWindow;
import ru.sberbank.homework.dragonblog.frontend.util.ImageUtils;
import ru.sberbank.homework.dragonblog.frontend.util.PostPanel;
import ru.sberbank.homework.dragonblog.model.Role;
import ru.sberbank.homework.dragonblog.security.SecurityUtils;
import ru.sberbank.homework.dragonblog.service.CommentServiceImpl;
import ru.sberbank.homework.dragonblog.service.PostServiceImpl;
import ru.sberbank.homework.dragonblog.service.UserServiceImpl;

@SpringView(name = ProfileView.NAME)
public class ProfileView extends HorizontalLayout implements View {
    public static final String NAME = "profile";

    private PostServiceImpl postService;
    private CommentServiceImpl commentService;

    private final VerticalLayout imageLayout = new VerticalLayout();
    private final VerticalLayout infoLayout = new VerticalLayout();
    private final HorizontalLayout nickLayout = new HorizontalLayout();

    private final Panel imagePanel = new Panel();
    private final FormLayout info = new FormLayout();
    private final FormLayout postsLayout = new FormLayout();

    private Label noPost = new Label("У пользователя еще нет постов");
    private Button favouriteBtn = new Button(VaadinIcons.CHECK, this::deleteFavourite);
    private Button nonFavouriteBtn = new Button(VaadinIcons.STAR, this::setFavourite);
    private Button adminBtn = new Button(VaadinIcons.USER_STAR, this::removeAdminRole);
    private Button userBtn = new Button(VaadinIcons.USER, this::addAdminRole);

    private UiUser user;
    private UiUser userSecurity;

    private final UserServiceImpl service;

    public ProfileView(UserServiceImpl service,
                       PostServiceImpl postService,
                       CommentServiceImpl commentService) {
        this.service = service;
        this.postService = postService;
        this.commentService = commentService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        initRoot();
        initCurrentUser();
        initImagePanel();
        initInfoPanel();
        initDeleteBtn();
        initAdminBtn();
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
        userSecurity = service.get(SecurityUtils.getUser().getId());
        if (user_id == null) {
            user = userSecurity;
        } else {
            user = service.get((long) user_id);
        }
    }

    private boolean checkFavourite(Long userSecrId, Long userId) {
        List<UiUser> favouriteUsers = service.findFavouriteUsers(userSecrId);
        List<Long> favUsersId = favouriteUsers.stream()
                .map(UiUser::getId)
                .collect(Collectors.toList());

        return favUsersId.contains(userId);
    }

    private void initImagePanel() {

        Image avatar = ImageUtils.imageFromByteArray(user.getAvatar());

        imagePanel.setPrimaryStyleName("image-panel");
        imagePanel.setContent(avatar);

        imageLayout.addComponent(imagePanel);
        imageLayout.setWidth(95, Unit.PERCENTAGE);
    }

    private void initAdminBtn() {
        if (SecurityUtils.hasRole(Role.ADMIN)) {
            if (user.getRoles().contains(Role.ADMIN)) {
                adminBtn.setVisible(true);
                userBtn.setVisible(false);
            } else {
                adminBtn.setVisible(false);
                userBtn.setVisible(true);
            }

            nickLayout.addComponent(userBtn);
            nickLayout.addComponent(adminBtn);
        }
    }

    private void addAdminRole(Button.ClickEvent event) {
        service.addRole(user.getId(), Role.ADMIN);
        userBtn.setVisible(false);
        adminBtn.setVisible(true);

        Notification addAdmin = new Notification("пользователю "
                + user.getNickname() + " теперь администратор");
        addAdmin.setDelayMsec(1000);
        addAdmin.setPosition(Position.TOP_CENTER);
        addAdmin.show(Page.getCurrent());
    }

    private void removeAdminRole(Button.ClickEvent event) {
        if (user.getId().equals(userSecurity.getId())) {
            Notification burden = new Notification("это твоё бремя");
            burden.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
            burden.setDelayMsec(2000);
            burden.setPosition(Position.TOP_CENTER);
            burden.show(Page.getCurrent());
        } else {
            service.deleteRole(user.getId(), Role.ADMIN);
            adminBtn.setVisible(false);
            userBtn.setVisible(true);

            Notification remAdmin = new Notification("пользователь "
                    + user.getNickname() + " больше не администратор");
            remAdmin.setDelayMsec(1000);
            remAdmin.setPosition(Position.TOP_CENTER);
            remAdmin.show(Page.getCurrent());
        }
    }

    private void initDeleteBtn() {
        if (SecurityUtils.hasRole(Role.ADMIN)) {
            Button deleteBtn = new Button("Удалить профиль");

            deleteBtn.addClickListener(e -> {
                DeleteWindow deleteWindow = new DeleteWindow("");
                deleteWindow.setValue("Удалить профиль пользователя" + " "
                        + user.getNickname());

                deleteWindow.addOkBtnListener( event -> {
                    VaadinSession.getCurrent().setAttribute("deleted", user.getNickname());
                    service.delete(user);
                    UI.getCurrent().getNavigator().navigateTo(SearchView.NAME);
                    deleteWindow.close();
                });

                getUI().addWindow(deleteWindow);
            });

            deleteBtn.setIcon(VaadinIcons.TRASH);
            deleteBtn.setStyleName(ValoTheme.BUTTON_BORDERLESS);
            nickLayout.addComponent(deleteBtn);
            nickLayout.setComponentAlignment(deleteBtn, Alignment.TOP_CENTER);
        }
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
        nickLayout.addComponent(nickname);

        Long userSecrId = userSecurity.getId();
        Long userId = user.getId();

        if (checkFavourite(userSecrId, userId)) {
            nonFavouriteBtn.setVisible(false);
        } else {
           favouriteBtn.setVisible(false);
        }

        if (userId.longValue() != userSecrId.longValue()) {
            nickLayout.addComponent(favouriteBtn);
            nickLayout.addComponent(nonFavouriteBtn);
        }

        info.addComponent(nickLayout);
        info.addComponent(tabSheet);
        infoLayout.addComponent(info);
    }

    private void deleteFavourite(Button.ClickEvent event) {
        service.deleteFavouriteUser(userSecurity.getId(), user.getId());
        favouriteBtn.setVisible(false);
        nonFavouriteBtn.setVisible(true);

        Notification notif = new Notification("вы отписались");
        notif.setDelayMsec(2000);
        notif.setPosition(Position.TOP_CENTER);
        notif.show(Page.getCurrent());
    }

    private void setFavourite(Button.ClickEvent event) {
        service.saveFavouriteUser(userSecurity.getId(), user.getId());
        nonFavouriteBtn.setVisible(false);
        favouriteBtn.setVisible(true);

        Notification notif =  new Notification("вы подписались");
        notif.setPosition(Position.TOP_CENTER);
        notif.setDelayMsec(2000);
        notif.show(Page.getCurrent());
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

        if(user.getId().equals(userSecurity.getId())) {
            displayCreatePostPanel();
        }
        List<UiPost> posts = getListPosts();

        if(posts.isEmpty()) {
            postsLayout.addComponent(noPost);
        }
        for (UiPost post : posts) {
            postsLayout.addComponent(formPanelPost(post));
        }
    }

    private List<UiPost> getListPosts() {
        return postService.getAllByUser(user.getId());
    }

    private Panel formPanelPost(UiPost post) {
        PostPanel postPanel = new PostPanel(postService, commentService, user);
        return postPanel.getPanelPost(post, userSecurity, postsLayout);
    }

    private void displayCreatePostPanel() {
        Panel panel = new Panel("Создание нового поста");
        panel.setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout();

        TextArea textArea = new TextArea();
        textArea.setMaxLength(1200);
        textArea.setPlaceholder("Начните писать ваш новый пост...");
        textArea.setSizeFull();

        Button create = new Button("Создать");
        create.setStyleName("data-about");

        create.addClickListener((Button.ClickListener) event2 -> {
            String newDescription = textArea.getValue();
            if (newDescription != null && !newDescription.isEmpty()) {
                textArea.setValue("");

                UiPost post = UiPost.builder()
                        .author(userSecurity)
                        .description(newDescription)
                        .postDateTime(LocalDateTime.now().format(DateTimeFormatter
                                .ofPattern("HH:mm:ss dd.MM.yyyy", Locale.getDefault())))
                        .build();

                post = postService.create(post, user.getId());

                if(post != null) {
                    postsLayout.addComponent(formPanelPost(post), 1);
                    postsLayout.removeComponent(noPost);
                }
            }
        });

        verticalLayout.addComponent(textArea);
        verticalLayout.addComponent(create);
        panel.setContent(verticalLayout);
        postsLayout.addComponent(panel);
    }
}
