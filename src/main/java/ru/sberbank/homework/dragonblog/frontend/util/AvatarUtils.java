package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

import java.io.ByteArrayInputStream;

public class AvatarUtils {

    public static Image imageFromByteArray(byte[] array) {
        Image avatar = new Image();
        avatar.setPrimaryStyleName("avatar");

        if (array != null) {
            StreamResource.StreamSource streamSource =
                    (StreamResource.StreamSource) () -> new ByteArrayInputStream(array);

            StreamResource imageResource = new StreamResource(streamSource, "");
            avatar.setSource(imageResource);
        } else {
            avatar.setSource(new ThemeResource("../metro/img/avatar2.jpg"));
        }

        return avatar;
    }
}
