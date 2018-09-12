package com.jariwala.securityjwtstarter.service.impl;

import com.jariwala.securityjwtstarter.model.User;
import com.jariwala.securityjwtstarter.repository.UserRepository;
import com.jariwala.securityjwtstarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component(value = "userDetailService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username :" + username + "not found");
        }
        return user;
    }

    @Override
    public User loadUserByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("UserId does not exist"));
    }

}
