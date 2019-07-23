package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

import java.io.ByteArrayInputStream;

import ru.sberbank.homework.dragonblog.frontend.model.UiUser;

public class AvatarUtils {
    public static Image setAvatarResource(UiUser user) {
        Image avatar = new Image();
        avatar.setPrimaryStyleName("avatar");

        byte[] avatarBytes = user.getAvatar();
        if (avatarBytes != null) {
            StreamResource.StreamSource streamSource =
                    (StreamResource.StreamSource) () -> new ByteArrayInputStream(avatarBytes);

            StreamResource imageResource = new StreamResource(streamSource, "");
            avatar.setSource(imageResource);
        } else {
            avatar.setSource(new ThemeResource("../metro/img/avatar2.jpg"));
        }

        return avatar;
    }
}
