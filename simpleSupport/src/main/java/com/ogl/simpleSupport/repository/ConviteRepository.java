package com.ogl.simpleSupport.repository;

import com.ogl.simpleSupport.model.ConviteFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConviteRepository extends JpaRepository<ConviteFuncionario, Long> {
    ConviteFuncionario findByTokenConvite(String tokenConvite);
}
