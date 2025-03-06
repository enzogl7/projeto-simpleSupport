package com.ogl.simpleSupport.controller;

import com.ogl.simpleSupport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("usuario", userService.getUsuarioLogado().getName());
        return "dashboard/home";
    }

    @GetMapping("/chamados")
    public String chamados(Model model) {
        return "dashboard/chamados";
    }

    @GetMapping("/empresa")
    public String empresa(Model model) {
        model.addAttribute("empresa", userService.getUsuarioLogado().getEmpresaResponsavel());
        return "dashboard/empresa";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        model.addAttribute("perfil", userService.getUsuarioLogado());
        if (userService.getUsuarioLogado().getEmpresa() != null) {
            model.addAttribute("empresa", userService.getUsuarioLogado().getEmpresa().getNome());
        } else {
            model.addAttribute("empresa", "---");
        }
        return "dashboard/perfil";
    }
}
