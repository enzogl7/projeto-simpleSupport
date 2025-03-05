package com.ogl.simpleSupport.model;

public record RegisterDTO(String nome, String sobrenome, String email, String telefone, String senha, UserRole role, String tipoUsuario) {
}
