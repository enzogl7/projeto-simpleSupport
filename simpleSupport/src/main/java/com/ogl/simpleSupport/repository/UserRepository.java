package com.ogl.simpleSupport.repository;

import com.ogl.simpleSupport.model.Empresa;
import com.ogl.simpleSupport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email);
    Optional<User> findByNumber(String number);

    List<User> findByEmpresa(Empresa empresa);

    @Query("SELECT u.empresa FROM usuarios u WHERE u.email = :email")
    Optional<Empresa> findEmpresaByEmail(@Param("email") String email);

}
