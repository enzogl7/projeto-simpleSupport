package com.ogl.simpleSupport.controller;

import com.ogl.simpleSupport.dto.EmpresaDTO;
import com.ogl.simpleSupport.model.Empresa;
import com.ogl.simpleSupport.service.EmpresaService;
import com.ogl.simpleSupport.service.MailService;
import com.ogl.simpleSupport.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/empresa")
@Controller
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/salvaredicao")
    public ResponseEntity salvarEdicaoEmpresa(@RequestBody EmpresaDTO data) {
        try {
            Empresa empresa = empresaService.findById(Long.parseLong(data.idEmpresa()));
            empresa.setNome(data.nomeEmpresa());
            empresa.setCnpj(data.cnpj());
            empresa.setRazaoSocial(data.razaoSocial());
            empresa.setEmailEmpresa(data.emailEmpresa());
            if (data.emailResponsavelEmpresa() != "" && userService.findByEmail(data.emailResponsavelEmpresa()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O usuário responsável pela empresa com o email informado não foi encontrado.");
            }
            empresa.setEmailResponsavel(data.emailResponsavelEmpresa());
            empresaService.salvarEdicao(empresa);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao editar empresa.");
        }
    }

    @GetMapping("/funcionarios")
    public String listarFuncionarios(Model model) {
        Empresa empresaResponsavel = (userService.getUsuarioLogado().getEmpresaResponsavel() != null)
                ? userService.getUsuarioLogado().getEmpresaResponsavel()
                : null;
        model.addAttribute("funcionarios", userService.findByEmpresa(empresaResponsavel));
        return "empresa/funcionarios";
    }

    @PostMapping("/convidarfuncionario")
    public ResponseEntity convidarFuncionario(@RequestParam("emailFuncionario") String emailFuncionario) {
        try {
            if (userService.findByEmail(emailFuncionario) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe um usuário com esse email.");
            }

            mailService.enviarEmail(emailFuncionario, "Convite para empresa | SimpleSupport", "", "emails/convite_funcionario");


            System.out.println("Funcionário convidado: " + emailFuncionario);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
