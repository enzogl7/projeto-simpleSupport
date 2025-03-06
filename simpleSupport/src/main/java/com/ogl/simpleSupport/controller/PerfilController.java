package com.ogl.simpleSupport.controller;

import com.ogl.simpleSupport.dto.EdicaoPerfilDTO;
import com.ogl.simpleSupport.model.User;
import com.ogl.simpleSupport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UserService userService;

    @PostMapping("/salvaredicao")
    public ResponseEntity salvarEdicao(@RequestBody EdicaoPerfilDTO data) {
        try {
            User user = userService.findById(Integer.valueOf(data.idUsuario()));

            if (isNotEmpty(data.nomeCompleto())) {
                user.setName(data.nomeCompleto());
            }

            if (isNotEmpty(data.email())) {
                user.setEmail(data.email());
            }

            if (isNotEmpty(data.telefone())) {
                user.setNumber(data.telefone());
            }

            userService.salvarEdicao(user);


            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao editar perfil.");
        }
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }


}
