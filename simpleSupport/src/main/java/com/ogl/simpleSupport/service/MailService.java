package com.ogl.simpleSupport.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmailConvite(String destinatario, String assunto, String nomeEmpresa, String linkConvite, String template) {
        try {
            Context context = new Context();
            context.setVariable("nomeEmpresa", nomeEmpresa);
            context.setVariable("linkConvite", linkConvite);

            String htmlContent = templateEngine.process(template, context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            return "Erro ao enviar email. " + e.getMessage();
        }
    }

    public void enviarEmailConfirmacaoConvite(String destinatario, String assunto, String nomeEmpresa, String template) {
        try {
            Context context = new Context();
            context.setVariable("nomeEmpresa", nomeEmpresa);

            String htmlContent = templateEngine.process(template, context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
