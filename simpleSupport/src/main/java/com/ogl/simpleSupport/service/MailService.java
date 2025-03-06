package com.ogl.simpleSupport.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmail(String destinatario, String assunto, String mensagem, String template) {
        try {
            Context context = new Context();
            context.setVariable("mensagem", mensagem);

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

    public void enviarEmailRegistroEmpresa(String destinatario, String assunto, String nomeEmpresa, String cnpj, LocalDate dataRegistro, String template) {
        try {
            Context context = new Context();
            context.setVariable("nomeEmpresa", nomeEmpresa);
            context.setVariable("cnpj", cnpj);
            String dataFormatada = dataRegistro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            context.setVariable("dataRegistro", dataFormatada);


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
