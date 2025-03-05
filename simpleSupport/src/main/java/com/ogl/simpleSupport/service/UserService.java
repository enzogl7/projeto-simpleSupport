package com.ogl.simpleSupport.service;

import com.ogl.simpleSupport.model.User;
import com.ogl.simpleSupport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User cadastrar(User user) {
        return userRepository.save(user);
    }

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByNumber(String number) {
        return userRepository.findByNumber(number);
    }

    public User getUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return (User) userRepository.findByEmail(username);
    }
}
