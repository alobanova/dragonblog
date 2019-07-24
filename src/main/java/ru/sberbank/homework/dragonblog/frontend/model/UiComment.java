package ru.sberbank.homework.dragonblog.frontend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.sberbank.homework.dragonblog.model.Post;
import ru.sberbank.homework.dragonblog.model.User;

@Getter
@Setter
@Builder
public class UiComment {
    private Long id;

    private UiUser author;

    private UiPost post;

    private String date;

    private String description;
}
