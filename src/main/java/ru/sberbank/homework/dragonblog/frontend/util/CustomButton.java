package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class CustomButton extends Button {

    public CustomButton(String caption) {
        super(caption);

        setPrimaryStyleName(ValoTheme.MENU_ITEM);
        setCaption(caption);
    }
}
