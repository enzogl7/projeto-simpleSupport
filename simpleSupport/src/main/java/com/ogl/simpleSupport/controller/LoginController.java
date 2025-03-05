package com.ogl.simpleSupport.controller;

import com.ogl.simpleSupport.model.*;
import com.ogl.simpleSupport.service.EmpresaService;
import com.ogl.simpleSupport.service.TokenService;
import com.ogl.simpleSupport.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody RegisterDTO data) {
        try {
            Empresa empresa = null;
            if (userService.findByEmail(data.email()) != null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email já cadastrado.");
            }

            if (userService.findByNumber(data.telefone()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Telefone já cadastrado.");
            }

            if (data.nomeEmpresa() != "" && data.cnpjEmpresa() != "" && data.emailEmpresa() != "" && data.razaoSocialEmpresa() != "") {
                Empresa empresaCriada = empresaService.save(data.nomeEmpresa(), data.cnpjEmpresa(), data.emailEmpresa(), data.razaoSocialEmpresa());
                empresa = empresaCriada;
            }

            String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
            String nomeCompleto = data.nome() + " " + data.sobrenome();
            User newUser = new User(nomeCompleto, data.email(), encryptedPassword, data.telefone(), data.role(), data.tipoUsuario(), empresa);

            userService.cadastrar(newUser);
            return ResponseEntity.ok().body("Usuario cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar usuário.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody AuthenticationDTO data, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        response.addCookie(cookie);

        User usuario = (User) userService.findByEmail(data.email());
        String tipoUsuario = usuario.getTipoUsuario();

        if (tipoUsuario.equals("usuario")) {
            // TODO: redirecionar para a plataforma de usuário
        }

        return ResponseEntity.ok().build();
    }
}
