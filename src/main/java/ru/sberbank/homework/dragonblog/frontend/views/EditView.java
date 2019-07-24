package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import ru.sberbank.homework.dragonblog.frontend.converter.UserConverter;
import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.frontend.util.AvatarUtils;
import ru.sberbank.homework.dragonblog.frontend.util.EditInfoFields;
import ru.sberbank.homework.dragonblog.security.SecurityUtils;
import ru.sberbank.homework.dragonblog.service.UserServiceImpl;

import static com.vaadin.ui.Upload.Receiver;
import static com.vaadin.ui.Upload.SucceededEvent;
import static com.vaadin.ui.Upload.SucceededListener;

@SpringView(name = EditView.NAME)
public class EditView extends VerticalLayout implements View {
    public static final String NAME = "register";

    private SpringNavigator navigator;

    private final HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
    private final Panel imagePanel = new Panel();
    private final FormLayout infoLayout = new FormLayout();
    private final VerticalLayout imageLayout = new VerticalLayout();

    private EditInfoFields fields;

    private final UserServiceImpl service;
    private UiUser user;
    private File newAvatar;
    private Image avatar;

    private final Label FAIL_LABEL = new Label("Заполните все обязательные поля");

    public EditView(SpringNavigator navigator,
                    final UserServiceImpl service) {
        this.navigator = navigator;
        this.service = service;
        this.user = service.get(SecurityUtils.getUser().getId());
    }

    @PostConstruct
    public void init() {
        initRoot();
        initInfoPanel();
        initImagePanel();
    }

    private void initRoot() {
        setSizeFull();
        setMargin(false);
        setSpacing(false);

        splitPanel.setFirstComponent(infoLayout);
        splitPanel.setSecondComponent(imageLayout);
        splitPanel.setSplitPosition(70, Unit.PERCENTAGE);
        splitPanel.setLocked(true);
        splitPanel.setStyleName("data-about");

        FAIL_LABEL.setStyleName(ValoTheme.LABEL_FAILURE);

        addComponent(splitPanel);
    }

    private void initImagePanel() {
        imageLayout.setSizeFull();

        avatar = AvatarUtils.imageFromByteArray(user.getAvatar());

        imagePanel.setPrimaryStyleName("image-panel");
        imagePanel.setContent(avatar);
        imagePanel.setSizeFull();

        AvatarReceiver receiver = new AvatarReceiver();
        Upload upload = new Upload("", receiver);
        upload.setButtonCaption("Загрузить");
        upload.setImmediateMode(true);
        upload.addSucceededListener(receiver);
        upload.setAcceptMimeTypes("image/jpeg");

        imageLayout.addComponent(imagePanel);
        imageLayout.addComponent(upload);
    }

    private void initInfoPanel() {

        infoLayout.setSizeFull();
        infoLayout.setSpacing(true);

        fields = new EditInfoFields(user);
        infoLayout.addComponent(fields.getSurname());
        infoLayout.addComponent(fields.getFirstName());
        infoLayout.addComponent(fields.getPatronymic());
        infoLayout.addComponent(fields.getGender());
        infoLayout.addComponent(fields.getBirthday());
        infoLayout.addComponent(fields.getCity());
        infoLayout.addComponent(fields.getAbout());

        Button saveBtn = new Button("Сохранить", this::save);
        saveBtn.setStyleName("no-margin");
        infoLayout.addComponent(saveBtn);
    }

    private void save(Button.ClickEvent event) {
        String firstName = fields.getFirstName().getValue();
        String surname = fields.getSurname().getValue();

        if (firstName.equals("") || surname.equals("")) {
            infoLayout.addComponent(FAIL_LABEL);
            return;
        }


        String patronymic = fields.getPatronymic().getValue();
        String gender = fields.getGender().getValue();
        String birthday = UserConverter.convertDateToString(fields.getBirthday().getValue());
        String city = fields.getCity().getValue();
        String about = fields.getAbout().getValue();

        byte[] bytes = null;
        if(newAvatar != null) {
            try {
                bytes = Files.readAllBytes(newAvatar.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bytes = user.getAvatar();
        }

        UiUser uiUser = UiUser.builder()
                .id(user.getId())
                .firstName(firstName)
                .surname(surname)
                .patronymic(patronymic)
                .gender(gender)
                .birthDate(birthday)
                .city(city)
                .description(about)
                .avatar(bytes)
                .build();


        service.update(user.getId(), uiUser);
        navigator.navigateTo(ProfileView.NAME);
    }

    private class AvatarReceiver implements Receiver, SucceededListener {

        @Override
        public OutputStream receiveUpload(final String filename, final String mimeType) {
            FileOutputStream fos = null;
            newAvatar = new File(filename);
            try {
                fos = new FileOutputStream(newAvatar);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fos;
        }

        @Override
        public void uploadSucceeded(final SucceededEvent event) {
            avatar.setSource(new FileResource(newAvatar));
        }
    }
}
