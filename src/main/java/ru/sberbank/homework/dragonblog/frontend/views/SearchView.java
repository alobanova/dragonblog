package ru.sberbank.homework.dragonblog.frontend.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

import ru.sberbank.homework.dragonblog.frontend.model.UiUser;
import ru.sberbank.homework.dragonblog.frontend.util.ImageUtils;
import ru.sberbank.homework.dragonblog.frontend.util.CustomButton;
import ru.sberbank.homework.dragonblog.security.SecurityUtils;
import ru.sberbank.homework.dragonblog.service.UserServiceImpl;

@SpringView(name = SearchView.NAME)
public class SearchView extends VerticalLayout implements View {
    public static final String NAME = "search";

    private final VerticalLayout usersLayout = new VerticalLayout();

    private final TextField searchField = new TextField();
    private final Button searchBtn = new Button(VaadinIcons.SEARCH, this::search);
    private final Button favouriteBtn = new Button(VaadinIcons.STAR, this::favourite);
    private final Panel userPanel = new Panel();

    private final UserServiceImpl userService;

    public SearchView(final UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setSizeFull();
        initSearchLayout();
        initUserLayout();
        initFavouriteView();

        if (VaadinSession.getCurrent().getAttribute("deleted") != null) {
            String deletedUsername = VaadinSession.getCurrent().getAttribute("deleted").toString();
            Notification notif = new Notification("профиль " + deletedUsername + " удален");
            VaadinSession.getCurrent().setAttribute("deleted", null);
            notif.setPosition(Position.TOP_CENTER);
            notif.setDelayMsec(2000);
            notif.show(Page.getCurrent());
        }
    }

    private void initSearchLayout() {
        searchField.setWidth(100, Unit.PERCENTAGE);
        searchField.setMaxLength(30);

        HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchBtn, favouriteBtn);
        searchLayout.setSizeFull();
        searchLayout.setExpandRatio(searchField, 1.0f);
        searchLayout.setExpandRatio(favouriteBtn, 1.0f);
        searchLayout.setMargin(new MarginInfo(false, false, true, false));

        userPanel.setWidth(100, Unit.PERCENTAGE);

        addComponents(searchLayout, usersLayout);
        setExpandRatio(searchLayout, 2);
    }

    private void initUserLayout() {
        usersLayout.setSizeFull();
        usersLayout.setMargin(false);
        usersLayout.setSpacing(true);

        setExpandRatio(usersLayout, 18);
    }

    private void search(Button.ClickEvent event) {
        String value = searchField.getValue();
        usersLayout.removeAllComponents();

        if (value.equals("")) {
            return;
        }

        List<UiUser> users = userService.findByPartOfNickname(value);

        fillUsers(users);
    }

    private void fillUsers(List<UiUser> users) {
        for (UiUser findUser : users) {
            Panel userPanel = new Panel();
            userPanel.setWidth(100, Unit.PERCENTAGE);

            CustomButton userButton = new CustomButton(String.format("%s %s", findUser.getSurname(),
                    findUser.getFirstName()));

            userButton.addClickListener(event -> {
                VaadinSession.getCurrent().setAttribute("user_id", findUser.getId());

                UI.getCurrent().getNavigator().navigateTo(ProfileView.NAME);
            });

            Panel imagePanel = new Panel(ImageUtils.imageFromByteArray(findUser.getAvatar()));
            imagePanel.setWidth(50, Unit.PIXELS);
            imagePanel.setStyleName(ValoTheme.PANEL_WELL);

            HorizontalLayout hl = new HorizontalLayout(imagePanel, userButton);
            hl.setSizeFull();
            hl.setMargin(true);
            hl.setComponentAlignment(imagePanel, Alignment.MIDDLE_LEFT);
            hl.setExpandRatio(imagePanel, 1);
            hl.setExpandRatio(userButton, 19);

            userPanel.setContent(hl);
            usersLayout.addComponent(userPanel);
        }
    }

    private void initFavouriteView () {
        long userId = SecurityUtils.getUser().getId();
        List<UiUser> favouriteUsers = userService.findFavouriteUsers(userId);

        if (!favouriteUsers.isEmpty()) {
            fillUsers(favouriteUsers);
        }
    }

    private void favourite(Button.ClickEvent event) {
        usersLayout.removeAllComponents();
        initFavouriteView();
    }
}
