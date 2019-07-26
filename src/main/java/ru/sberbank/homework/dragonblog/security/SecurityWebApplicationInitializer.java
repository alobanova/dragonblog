package ru.sberbank.homework.dragonblog.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import ru.sberbank.homework.dragonblog.config.SecurityConfig;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;

/**
 * Created by Mart
 * 20.07.2019
 **/
@WebListener
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        super.beforeSpringSecurityFilterChain(servletContext);
        servletContext.addListener(new HttpSessionEventPublisher());
    }
}
