package com.ogl.simpleSupport.repository;

import com.ogl.simpleSupport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email);
    Optional<User> findByNumber(String number);
}
