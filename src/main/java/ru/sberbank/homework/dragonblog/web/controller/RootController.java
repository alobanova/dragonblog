package ru.sberbank.homework.dragonblog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created by Mart
 * 01.07.2019
 **/
@Controller
public class RootController {
    @GetMapping("/")
    public String root() {
        return "redirect:profile";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
