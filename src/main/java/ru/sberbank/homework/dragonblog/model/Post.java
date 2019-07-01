package ru.sberbank.homework.dragonblog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mart
 * 30.06.2019
 **/
@NoArgsConstructor
public class Post extends AbstractNamedEntity {
    @Getter @Setter
    private User author;

    @Getter @Setter
    private LocalDateTime postDateTime;

    @Getter @Setter
    private String Description;

    @Getter @Setter
    private byte[] photo;

    @Getter @Setter
    private List<Comment> comments;
}
