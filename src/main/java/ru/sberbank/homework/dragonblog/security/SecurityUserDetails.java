package ru.sberbank.homework.dragonblog.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sberbank.homework.dragonblog.model.Role;
import ru.sberbank.homework.dragonblog.model.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mart
 * 21.07.2019
 **/
public class SecurityUserDetails extends User implements UserDetails {
    private static final long serialVersionUID = 1L;

    public SecurityUserDetails(User user) {
        if(user==null) return;
        this.setId(user.getId());
        this.setNickname(user.getNickname());
        this.setPassword(user.getPassword());
        this.setRoles(user.getRoles());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : super.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return super.getNickname();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
