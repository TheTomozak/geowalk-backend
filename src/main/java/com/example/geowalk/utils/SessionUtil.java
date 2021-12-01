package com.example.geowalk.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SessionUtil implements ISessionUtil {

    @Override
    public String getLoggedUserUsername() {
        Object loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedUser instanceof UserDetails) {
            return ((UserDetails) loggedUser).getUsername();
        }
        return null;
    }

    @Override
    public String getLoggedUserEncryptedPassword() {
        Object loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedUser instanceof UserDetails) {
            return ((UserDetails) loggedUser).getPassword();
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getLoggedUserAuthority() {
        Object loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedUser instanceof UserDetails) {
            return ((UserDetails) loggedUser).getAuthorities();
        }
        return null;
    }
}
