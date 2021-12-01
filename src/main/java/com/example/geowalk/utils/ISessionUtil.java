package com.example.geowalk.utils;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface ISessionUtil {

    String getLoggedUserUsername();

    String getLoggedUserEncryptedPassword();

    Collection<? extends GrantedAuthority> getLoggedUserAuthority();
}
