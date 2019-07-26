package ru.sberbank.homework.dragonblog.frontend.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Mart 01.07.2019
 **/

@Getter
@Builder
public class UiUser {

    private Long id;

    private String nickname;

    private String firstName;

    private String surname;

    private String patronymic;

    private String gender;

    private String birthDate;

    private String city;

    private String description;

    private byte[] avatar;

    private List<UiPost> posts;
}
