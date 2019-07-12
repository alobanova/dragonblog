package ru.sberbank.homework.dragonblog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mart
 * 30.06.2019
 **/
@Entity
@Table(name = "Post")
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Post_id_seq")
    @SequenceGenerator(name = "Post_id_seq", sequenceName = "Post_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author")
    private User author;

    @Column(name = "date")
    private LocalDateTime postDateTime;

    @Column(name = "description")
    private String Description;

    private byte[] photo = null;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
