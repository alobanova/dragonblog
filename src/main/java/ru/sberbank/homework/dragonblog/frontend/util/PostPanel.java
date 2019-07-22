package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.sberbank.homework.dragonblog.frontend.model.UiPost;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.service.PostServiceImpl;

public class PostPanel {
    private PostServiceImpl postService;

    private UiUser user;
    private UiPost post;
    private FormLayout contentLayout;

    private Panel panel;
    private Label text;
    private TextArea textArea;
    private Button delete;
    private Button save;
    private Button edit;

    public PostPanel(PostServiceImpl postService) {
        this.postService = postService;
    }

    public Panel getPanelPost(UiPost post, UiUser user, FormLayout contentLayout) {
        this.post = post;
        this.user = user;
        this.contentLayout = contentLayout;

        String postDescription = post.getDescription();

        initPanel();
        initText(postDescription);
        initTextArea(postDescription);
        initButtonDelete();
        initButtonEdit();
        initButtonSave();

        HorizontalLayout buttons = new HorizontalLayout();
        VerticalLayout postContent = new VerticalLayout();

        buttons.setSizeFull();

        buttons.addComponent(save);

        HorizontalLayout settingLayout = new HorizontalLayout();
        settingLayout.addComponent(edit);
        settingLayout.addComponent(delete);

        buttons.addComponent(settingLayout);
        buttons.setComponentAlignment(settingLayout, Alignment.MIDDLE_RIGHT);


        postContent.addComponent(text);
        postContent.addComponent(textArea);
        postContent.addComponent(buttons);

        panel.setContent(postContent);

        return panel;
    }

    private void initPanel() {
        UiUser author = post.getAuthor();

        panel = new Panel(author.getFirstName()
                + " "
                + author.getSurname()
                + " @"
                + author.getNickname()
                + " "
                + post.getPostDateTime());

        panel.setSizeFull();
    }

    private void initText(String description) {
        text = new Label(description, ContentMode.TEXT);
        text.setSizeFull();
    }

    private void initTextArea(String description) {
        textArea = new TextArea();
        textArea.setValue(description);
        textArea.setSizeFull();
        textArea.setVisible(false);
    }

    private void initButtonDelete() {
        delete = new Button();
        delete.setIcon(VaadinIcons.TRASH);
        delete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        delete.setWidth("10px");
        delete.addClickListener((Button.ClickListener) event1 -> deletePost());
    }

    private void deletePost() {
        postService.delete(post.getId(), user.getId());
        contentLayout.removeComponent(panel);
    }

    private void initButtonEdit() {
        edit = new Button();
        edit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        edit.setWidth("10px");
        edit.setIcon(VaadinIcons.EDIT);


        edit.addClickListener((Button.ClickListener) event1 -> {
            text.setVisible(false);
            textArea.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
            save.setVisible(true);
        });
    }

    private void initButtonSave() {
        save = new Button("Сохранить");
        save.setVisible(false);
        save.addClickListener((Button.ClickListener) event2 -> updatePost());
    }

    private void updatePost() {
        String newDescription = textArea.getValue();
        post.setDescription(newDescription);
        postService.update(post, user.getId());
        text.setValue(newDescription);
        text.setVisible(true);
        textArea.setVisible(false);

        edit.setVisible(true);
        delete.setVisible(true);
        save.setVisible(false);
    }

}
