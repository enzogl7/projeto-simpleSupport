package com.ogl.simpleSupport.service;

import com.ogl.simpleSupport.model.Empresa;
import com.ogl.simpleSupport.model.User;
import com.ogl.simpleSupport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<User> findByEmpresa(Empresa empresa) {
        return userRepository.findByEmpresa(empresa);
    }

    public void salvarEdicao(User user) {
        userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).get();
    }

    public Optional<Empresa> findEmpresaByEmail(String email) {
        return userRepository.findEmpresaByEmail(email);
    }

}
