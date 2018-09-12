package com.jariwala.securityjwtstarter.service;

import com.jariwala.securityjwtstarter.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    User loadUserByUserId(Long id);
}
