package ru.sberbank.homework.dragonblog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by Mart
 * 30.06.2019
 **/
@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString (exclude =  {"posts"})
@Getter
@Setter
@Transactional
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq" , allocationSize = 1)
    private Long id;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDateTime birthDate;

    @Column(name = "city")
    private String city;

    @Column(name = "about_me")
    private String description;

    //@JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Post> posts;

    //private byte[] photo;

    //private Role role;

//    @Getter @Setter
//    private List<User> favorite;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname);
    }
}
