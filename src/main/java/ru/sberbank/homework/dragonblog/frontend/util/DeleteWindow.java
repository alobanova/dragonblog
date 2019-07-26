package ru.sberbank.homework.dragonblog.frontend.util;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DeleteWindow extends Window {

    private final Button okBtn = new Button("Да");
    private final Button cancelBtn = new Button("Нет", event1 -> close());

    public DeleteWindow(String caption) {
        super(caption);

        setModal(true);
        setCaption("Удаление профиля");
        setWidth(400, Unit.PIXELS);
        setHeight(150, Unit.PIXELS);

        Label deleteLabel = new Label("Вы уверены, что хотите удалить свой профиль?");

        okBtn.setStyleName("data-about");
        cancelBtn.setStyleName("data-about");

        HorizontalLayout buttonLayout = new HorizontalLayout(okBtn, cancelBtn);
        buttonLayout.setSizeFull();
        buttonLayout.setComponentAlignment(okBtn, Alignment.MIDDLE_RIGHT);
        buttonLayout.setComponentAlignment(cancelBtn, Alignment.MIDDLE_LEFT);

        VerticalLayout vl = new VerticalLayout(deleteLabel, buttonLayout);
        vl.setSizeFull();

        setContent(vl);
    }

    public void addOkBtnListener(Button.ClickListener listener) {
        okBtn.addClickListener(listener);
    }
}
