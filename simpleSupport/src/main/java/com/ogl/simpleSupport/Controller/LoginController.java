package com.ogl.simpleSupport.Controller;

import com.ogl.simpleSupport.Model.User;
import com.ogl.simpleSupport.Repository.UserRepository;
import com.ogl.simpleSupport.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String home() {
        return "login";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@RequestParam String nome,
                                    @RequestParam String sobrenome,
                                    @RequestParam String email,
                                    @RequestParam String senha) {
        try {
            if (userService.findByEmail(email).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email já cadastrado.");
            }

            User user = new User();
            user.setName(nome + " " + sobrenome);
            user.setEmail(email);
            user.setPassword(senha);

            userService.cadastrar(user);
            return ResponseEntity.ok().body("Usuario cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar usuário.");
        }
    }
}
