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
public class User extends AbstractNamedEntity {
    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String surname;

    @Getter @Setter
    private Gender gender;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private byte[] photo;

    @Getter @Setter
    private LocalDateTime birthDate;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private Role role;

    @Getter @Setter
    private List<Post> posts;

//    @Getter @Setter
//    private List<User> favorite;
}
