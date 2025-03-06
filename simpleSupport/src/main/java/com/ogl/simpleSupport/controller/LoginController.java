package com.ogl.simpleSupport.controller;

import com.ogl.simpleSupport.dto.AuthenticationDTO;
import com.ogl.simpleSupport.dto.RegisterDTO;
import com.ogl.simpleSupport.model.*;
import com.ogl.simpleSupport.service.EmpresaService;
import com.ogl.simpleSupport.service.MailService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

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

    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestBody RegisterDTO data) {
        try {
            Empresa empresaCriada = null;
            Optional<Empresa> empresaUsuario = userService.findEmpresaByEmail(data.emailResponsavelEmpresa()); // usado para verificar se a empresa do usuário é igual a que ele será setado como responsável

            // INICIO EXCEÇÕES
            if (userService.findByEmail(data.email()) != null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email já cadastrado.");
            }

            if (userService.findByNumber(data.telefone()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Telefone já cadastrado.");
            }

            if (data.emailResponsavelEmpresa() != "" && userService.findByEmail(data.emailResponsavelEmpresa()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O usuário responsável pela empresa com o email informado não foi encontrado.");
            }

            if (data.emailResponsavelEmpresa() != "" && !empresaService.findByEmailResponsavel(data.emailResponsavelEmpresa()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.IM_USED).body("O usuário com o email informado já é responsável por uma empresa.");
            }
            // FIM EXCEÇÕES

            // criação de empresa
            if (data.nomeEmpresa() != "" && data.cnpjEmpresa() != "" && data.emailEmpresa() != "" && data.razaoSocialEmpresa() != "" && data.emailResponsavelEmpresa() != "") {
                empresaCriada = new Empresa(data.nomeEmpresa(), data.cnpjEmpresa(), data.emailEmpresa(), data.razaoSocialEmpresa(), data.emailResponsavelEmpresa());


                if (empresaUsuario.isPresent() && !empresaCriada.getCnpj().equals(empresaUsuario.get().getCnpj())) {
                    return ResponseEntity.status(HttpStatus.FOUND).body("A empresa do usuário com o email informado é diferente.");
                }

                User usuarioResponsavel = (User) userService.findByEmail(data.emailResponsavelEmpresa());
                if (usuarioResponsavel.getEmpresa() == null) {
                    usuarioResponsavel.setEmpresa(empresaCriada);
                }
                usuarioResponsavel.setEmpresaResponsavel(empresaCriada);
                empresaService.save(empresaCriada);
                mailService.enviarEmailRegistroEmpresa(data.emailEmpresa(), "Empresa registrada no sistema | SimpleSupport",
                        data.nomeEmpresa(), data.cnpjEmpresa(), LocalDate.now(), "emails/notificacao_empresa");
            }

            // criação usuário padrão
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
            String nomeCompleto = data.nome() + " " + data.sobrenome();
            User newUser = new User(nomeCompleto, data.email(), encryptedPassword, data.telefone(), data.role(), data.tipoUsuario(), empresaCriada);
            userService.cadastrar(newUser);

            return ResponseEntity.ok().body("Usuario cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao cadastrar usuário.");
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
