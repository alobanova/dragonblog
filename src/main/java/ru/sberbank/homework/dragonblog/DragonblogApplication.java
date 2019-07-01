package ru.sberbank.homework.dragonblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sberbank.homework.dragonblog.repository.JpaUserRepository;
import ru.sberbank.homework.dragonblog.repository.UserRepository;
import ru.sberbank.homework.dragonblog.service.UserService;
import ru.sberbank.homework.dragonblog.service.UserServiceImpl;

@SpringBootApplication
public class DragonblogApplication {
	public static void main(String[] args) {
		SpringApplication.run(DragonblogApplication.class, args);
	}
}
