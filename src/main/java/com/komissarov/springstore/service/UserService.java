package com.komissarov.springstore.service;

import com.komissarov.springstore.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    User findUserByLogin(String login);
}
