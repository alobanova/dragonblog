package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import ru.sberbank.homework.dragonblog.frontend.model.UiComment;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.service.CommentServiceImpl;

public class CommentPanel {
    private CommentServiceImpl commentService;

    private UiUser user;
    private UiUser userSecurity;
    private UiComment comment;
    private VerticalLayout contentLayout;

    private VerticalLayout commentLayout = new VerticalLayout();
    private HorizontalLayout title = new HorizontalLayout();
    private FormLayout textLayout = new FormLayout();
    private Label text;
    private TextArea textArea;
    private Button delete;
    private Button save;
    private Button edit;

    public CommentPanel(CommentServiceImpl commentService, UiUser user) {
        this.commentService = commentService;
        this.user = user;
    }

    public VerticalLayout getPanelComment(UiComment comment, UiUser author, VerticalLayout contentLayout) {
        this.comment = comment;
        this.userSecurity = author;
        this.contentLayout = contentLayout;

        init();

        commentLayout.setSizeFull();
        commentLayout.setMargin(false);
        commentLayout.setStyleName("layout-with-border");

        commentLayout.addComponent(title);
        commentLayout.addComponent(textLayout);

        if (comment.getAuthor().getId().equals(userSecurity.getId())
                || user.getId().equals(userSecurity.getId())) {
            commentLayout.addComponent(textArea);
            HorizontalLayout buttons = formButtonLayout();
            commentLayout.addComponent(buttons);
        }

        return commentLayout;
    }

    private void init() {
        initTitle();
        initTextLayout();

        if (comment.getAuthor().getId().equals(userSecurity.getId())
                || user.getId().equals(userSecurity.getId())) {
            initTextArea();
            initButtonSave();
            initButtonDelete();
            initButtonEdit();
        }
    }

    private HorizontalLayout formButtonLayout() {
        HorizontalLayout buttons = new HorizontalLayout();

        buttons.setSizeFull();
        buttons.setMargin(false);

        buttons.addComponent(save);
        buttons.addComponent(edit);
        buttons.addComponent(delete);

        if(!comment.getAuthor().getId().equals(userSecurity.getId())) {
            edit.setVisible(false);
        }

        buttons.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
        buttons.setComponentAlignment(edit, Alignment.MIDDLE_RIGHT);
        buttons.setExpandRatio(edit, 1.0f);
        return buttons;
    }

    private void initTitle() {
        UiUser author = comment.getAuthor();

        HorizontalLayout layout = new HorizontalLayout();

        Image avatar = AvatarUtils.imageFromByteArray(author.getAvatar());
        avatar.setHeight(50, Sizeable.Unit.PIXELS);
        avatar.setWidth(50, Sizeable.Unit.PIXELS);
        avatar.setStyleName("image-panel");

        String name = author.getFirstName() + " " + author.getSurname() + " " + author.getNickname();
        Label nameComment = new Label(name);
        nameComment.setStyleName("comment-name-font");

        Label date = new Label(comment.getDate());
        date.setStyleName("data-about");

        VerticalLayout info = new VerticalLayout();
        info.setSpacing(false);
        info.setMargin(false);
        info.setWidth("100%");
        info.addComponent(nameComment);
        info.addComponent(date);

        title.addComponent(avatar);
        title.addComponent(info);
        title.setSizeFull();
        title.setExpandRatio(info, 1.0f);
        title.setStyleName("comment-title");
        title.addComponent(layout);
    }

    private void initTextLayout() {
        text = new Label(comment.getDescription(), ContentMode.TEXT);
        text.setSizeFull();

        textLayout.addComponent(text);
        textLayout.setSizeFull();
        textLayout.setMargin(false);
        textLayout.setSpacing(false);
    }

    private void initTextArea() {
        textArea = new TextArea();
        textArea.setValue(comment.getDescription());
        textArea.setSizeFull();
        textArea.setVisible(false);
    }

    private void initButtonDelete() {
        delete = new Button();
        delete.setIcon(VaadinIcons.TRASH);
        delete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        delete.setWidth("10px");
        delete.addClickListener((Button.ClickListener) event1 -> deleteComment());
    }

    private void deleteComment() {
        commentService.delete(comment.getId());
        contentLayout.removeComponent(commentLayout);
    }

    private void initButtonEdit() {
        edit = new Button();
        edit.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        edit.setWidth("10px");
        edit.setIcon(VaadinIcons.EDIT);


        edit.addClickListener((Button.ClickListener) event1 -> {
            text.setVisible(false);
            edit.setVisible(false);
            textArea.setVisible(true);
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
        if (newDescription != null && !newDescription.isEmpty()) {
            comment.setDescription(newDescription);
            commentService.update(comment.getId(), newDescription);
            text.setValue(newDescription);
            text.setVisible(true);
            textArea.setVisible(false);

            edit.setVisible(true);
            delete.setVisible(true);
            save.setVisible(false);
        }
    }


}
