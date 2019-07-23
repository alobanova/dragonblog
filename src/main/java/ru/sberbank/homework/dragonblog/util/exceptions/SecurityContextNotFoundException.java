package ru.sberbank.homework.dragonblog.util.exceptions;

/**
 * Created by Mart
 * 22.07.2019
 **/
public class SecurityContextNotFoundException extends RuntimeException {
    public SecurityContextNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public SecurityContextNotFoundException(String msg) {
        super(msg);
    }
}
