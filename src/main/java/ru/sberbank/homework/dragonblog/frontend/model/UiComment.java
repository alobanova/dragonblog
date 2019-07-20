package ru.sberbank.homework.dragonblog.frontend.model;

import lombok.Builder;

@Builder
public class UiComment {
    private Long id;

    private String date;

    private String description;
}
