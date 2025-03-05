package com.ogl.simpleSupport.controller;

import com.ogl.simpleSupport.dto.EmpresaDTO;
import com.ogl.simpleSupport.model.ConviteFuncionario;
import com.ogl.simpleSupport.model.Empresa;
import com.ogl.simpleSupport.model.User;
import com.ogl.simpleSupport.repository.ConviteRepository;
import com.ogl.simpleSupport.service.EmpresaService;
import com.ogl.simpleSupport.service.MailService;
import com.ogl.simpleSupport.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/empresa")
@Controller
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ConviteRepository conviteRepository;

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
    public ResponseEntity convidarFuncionario(@RequestParam("emailFuncionario") String emailFuncionario, HttpServletRequest request) {
        try {
            if (userService.findByEmail(emailFuncionario) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe um usuário com esse email.");
            }

            Empresa empresaResponsavel = userService.getUsuarioLogado().getEmpresaResponsavel();
            String nomeEmpresaResponsavel = empresaResponsavel.getNome();
            String tokenConvite = UUID.randomUUID().toString();

            ConviteFuncionario convite = new ConviteFuncionario();
            convite.setEmailFuncionario(emailFuncionario);
            convite.setTokenConvite(tokenConvite);
            convite.setEmpresaResponsavel(empresaResponsavel);
            convite.setDataExpiracao(LocalDateTime.now().plusDays(7));
            convite.setAceito(false);
            conviteRepository.save(convite);

            String serverUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
            String linkConvite = serverUrl + "/empresa/validar-convite?token=" + tokenConvite;

            mailService.enviarEmailConvite(emailFuncionario, "Convite para empresa | SimpleSupport", nomeEmpresaResponsavel, linkConvite, "emails/convite_funcionario");

            System.out.println("Funcionário convidado: " + emailFuncionario);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/validar-convite")
    public String validarConvite(@RequestParam("token") String tokenConvite, Model model) {
        ConviteFuncionario convite = conviteRepository.findByTokenConvite(tokenConvite);

        if (convite == null || convite.isAceito() || convite.getDataExpiracao().isBefore(LocalDateTime.now())) {
            return "/convite/erro_convite";
        }

        if (!userService.getUsuarioLogado().getEmail().equals(convite.getEmailFuncionario())) {
            return "/convite/erro_convite";
        }

        model.addAttribute("convite", convite);
        model.addAttribute("empresa", convite.getEmpresaResponsavel().getNome());

        return "/convite/aceitar_convite";
    }

    @PostMapping("/aceitar-convite")
    public String aceitarConvite(@RequestParam("token") String token) {
        ConviteFuncionario convite = conviteRepository.findByTokenConvite(token);

        if (convite == null || convite.isAceito() || convite.getDataExpiracao().isBefore(LocalDateTime.now())) {
            return "/convite/erro_convite";
        }

        User novoUsuario = (User) userService.findByEmail(convite.getEmailFuncionario());
        novoUsuario.setEmpresa(convite.getEmpresaResponsavel());
        userService.salvarEdicao(novoUsuario);

        convite.setAceito(true);
        conviteRepository.save(convite);

        // ENVIO PARA RESPONSÁVEL
        mailService.enviarEmailConfirmacaoConvite(convite.getEmpresaResponsavel().getEmailResponsavel(),
                "Convite aceito por funcionário | SimpleSupport",
                convite.getEmpresaResponsavel().getNome(), "emails/notificacao_convite_aceito_responsavel");

        // ENVIO PARA FUNCIONÁRIO
        mailService.enviarEmailConfirmacaoConvite(convite.getEmailFuncionario(),
                "Convite aceito! | SimpleSupport",
                convite.getEmpresaResponsavel().getNome(), "emails/notificacao_convite_aceito_funcionario");

        return "/login";
    }

    @PostMapping("/removerfuncionario")
    public ResponseEntity removerFuncionario(@RequestParam("idFuncionario") String idFuncionario) {
        try {
            User user = userService.findById(Integer.valueOf(idFuncionario));
            user.setEmpresa(null);
            userService.salvarEdicao(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
