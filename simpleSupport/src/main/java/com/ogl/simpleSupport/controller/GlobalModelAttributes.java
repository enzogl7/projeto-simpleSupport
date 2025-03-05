package com.ogl.simpleSupport.controller;

import com.ogl.simpleSupport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void adicionarNomeEmpresaAoModelo(Model model) {
        var usuarioLogado = userService.getUsuarioLogado();

        if (usuarioLogado != null && usuarioLogado.getEmpresa() != null) {
            model.addAttribute("nomeEmpresaNavbar", usuarioLogado.getEmpresa().getNome());
        } else {
            model.addAttribute("nomeEmpresaNavbar", "");
        }
        if (userService.getUsuarioLogado() != null) {
            if (userService.getUsuarioLogado().getEmpresaResponsavel() != null) {
                model.addAttribute("responsavelPorEmpresa", true);
            } else {
                model.addAttribute("responsavelPorEmpresa", false);
            }
        }
    }
}
