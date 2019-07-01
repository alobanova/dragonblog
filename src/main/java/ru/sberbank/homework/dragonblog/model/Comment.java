package ru.sberbank.homework.dragonblog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by Mart
 * 30.06.2019
 **/
@NoArgsConstructor
public class Comment extends AbstractBaseEntity {
    @Getter @Setter
    private Post post;

    @Getter @Setter
    private User author;

    @Getter @Setter
    private LocalDateTime dateTime;

    @Getter @Setter
    private String text;

}
