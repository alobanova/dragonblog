package ru.sberbank.homework.dragonblog.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by Mart
 * 30.06.2019
 **/
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "post", "author"})
@ToString(exclude = {"post", "author"})
@Table(name="COMMENT")
@Entity
@SequenceGenerator(name = "COMMENT_SEQ", sequenceName = "COMMENT_SEQ", allocationSize = 5)
public class Comment {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "COMMENT_SEQ")
    private Long id;

    @Column(nullable = false)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(nullable = false)
    @JoinColumn(name = "USER_ID")
    private User author;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String text;

}
