package ru.sberbank.homework.dragonblog.model;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

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
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "post", "author"})
@ToString(exclude = {"post", "author"})
@Table(name="comments")
@Entity
@SequenceGenerator(name = "COMMENT_SEQ", sequenceName = "COMMENT_SEQ", allocationSize = 1)
@Transactional
public class Comment {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "COMMENT_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_POST")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_AUTHOR")
    private User author;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String description;

}
