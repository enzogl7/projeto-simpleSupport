package com.ogl.simpleSupport.dto;

import com.ogl.simpleSupport.model.UserRole;

public record RegisterDTO(String nome, String sobrenome, String email, String telefone, String senha, UserRole role,
                          String tipoUsuario, String nomeEmpresa, String cnpjEmpresa, String emailEmpresa, String razaoSocialEmpresa, String emailResponsavelEmpresa) {
}
