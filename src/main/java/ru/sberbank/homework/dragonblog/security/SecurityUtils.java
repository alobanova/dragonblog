package ru.sberbank.homework.dragonblog.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.sberbank.homework.dragonblog.model.Role;
import ru.sberbank.homework.dragonblog.model.User;
import ru.sberbank.homework.dragonblog.util.exceptions.SecurityContextNotFoundException;

import java.io.Serializable;


/**
 * Created by Mart
 * 22.07.2019
 **/
public class SecurityUtils  implements Serializable {
    private static final long serialVersionUID = 1L;

    public SecurityUtils() {
    }

    public static boolean isLoggedIn() {
        Authentication authentication = authentication();
        if(authentication==null) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    public static boolean hasRole(Role role) {
        Authentication authentication = authentication();
        if(authentication==null) {
            return false;
        }
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.name());
        boolean hasRole = authentication.getAuthorities().contains(grantedAuthority);
        return hasRole;
    }

    public static User getUser() {
        Authentication authentication = authentication();
        if(authentication==null) {
            return new User();
        }
        User user = (User)authentication.getPrincipal();
        return user;
    }

    @SuppressWarnings("unchecked")
    private static Authentication authentication() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if(securityContext==null) {
            throw new SecurityContextNotFoundException("SecurityContext Not Found Exception");
        }

        final Authentication authentication = securityContext.getAuthentication();
        if(authentication==null) {
            throw new SecurityContextNotFoundException("SecurityContext Authentication Not Found Exception");
        }
        return authentication;
    }
}
