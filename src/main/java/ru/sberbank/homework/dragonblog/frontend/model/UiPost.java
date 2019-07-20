package ru.sberbank.homework.dragonblog.frontend.model;

import lombok.Builder;
import lombok.Getter;
import ru.sberbank.homework.dragonblog.model.User;

import java.util.List;

@Getter
@Builder
public class UiPost {

    private Long id;

    private User author;

    private String postDateTime;

    private String description;

    private List<UiComment> comments;
}
