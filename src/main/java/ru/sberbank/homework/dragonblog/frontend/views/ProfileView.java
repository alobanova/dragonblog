package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.model.User;
import ru.sberbank.homework.dragonblog.service.CommentService;
import ru.sberbank.homework.dragonblog.service.PostService;
import ru.sberbank.homework.dragonblog.service.UserService;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringView(name = ProfileView.NAME)
public class ProfileView extends HorizontalLayout implements View {
    public static final String NAME = "profile";

    private SpringNavigator navigator;

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    //С ним надо чтото решать.
    private User user;

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

        final VerticalLayout contentLayout = new VerticalLayout();
        //Тут через секьюрити надо получить юзера. Или наверху это вообще сделать.
        user = userService.get(2);

        displayCreatePostPanel(contentLayout);

        for (Post post : getListPosts()) {
            displayPost(post, contentLayout);
        }

        posts.setContent(contentLayout);

    }

    private List<Post> getListPosts() {
        return postService.getAllByUser(user.getId());
    }

    private void displayCreatePostPanel(VerticalLayout contentLayout) {
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

            //Чтото тут с проверкой не так на автора.. вседа же тру будет
            Post post = new Post();
            post.setAuthor(user);
            post.setDescription(newDescription);
            post.setPostDateTime(LocalDateTime.now());

            postService.create(post, user.getId());

            displayPost(post, contentLayout);
        });

        verticalLayout.addComponent(textArea);
        verticalLayout.addComponent(create);
        panel.setContent(verticalLayout);
        contentLayout.addComponent(panel);

    }

    private void displayPost(Post post, VerticalLayout contentLayout) {
        Panel panelPost = getPanelPost(post, contentLayout);
        //Тут типо чтобы новый пост всегда после панели создания рисовался.
        //Надо подумать над другим способом.
        contentLayout.addComponent(panelPost, 1);
    }

    private Panel getPanelPost(Post post, VerticalLayout contentLayout) {
        //ЭТО ПАНЕЛЬ И ЕЕ НАСТРОЙКИ
        Panel panel = new Panel(user.getFirstName()
                + " "
                + user.getSurname()
                + " @"
                + user.getNickname()
                + " "
                + post.getPostDateTime().format(DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mm")));

        panel.setSizeFull();

        //ТУТ НАПОЛНЕНИЕ ПАНЕЛИ КОНТЕНТОМ
        VerticalLayout postContent = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();

        String postDescription = post.getDescription();

        Label text = new Label(postDescription, ContentMode.TEXT);
        text.setSizeFull();

        //РЕДАКТИРОВАНИЕ ЗАПИСИ
        TextArea textArea = new TextArea();
        textArea.setValue(postDescription);
        textArea.setSizeFull();
        textArea.setVisible(false);

        // ТУТ РАЗБОРКИ С КНОПОЧКАМИ
        Button delete = new Button("Удалить");
        Button save = new Button("Сохранить");
        Button edit = new Button("Редактировать");
        save.setVisible(false);

        save.addClickListener((Button.ClickListener) event2 -> {
            String newDescription = textArea.getValue();
            post.setDescription(newDescription);
            postService.update(post, user.getId());
            text.setValue(newDescription);
            text.setVisible(true);
            textArea.setVisible(false);

            edit.setVisible(true);
            delete.setVisible(true);
            save.setVisible(false);
        });

        delete.addClickListener((Button.ClickListener) event1 -> {
            postService.delete(post.getId(), user.getId());
            contentLayout.removeComponent(panel);
        });

        edit.addClickListener((Button.ClickListener) event1 -> {
            text.setVisible(false);
            textArea.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
            save.setVisible(true);
        });

        //ТУТ ВСЕ ДОБАВЛЯЕМ
        buttons.addComponent(edit);
        buttons.addComponent(delete);
        buttons.addComponent(save);

        postContent.addComponent(text);
        postContent.addComponent(textArea);
        postContent.addComponent(buttons);

        panel.setContent(postContent);
        return panel;
    }
}
