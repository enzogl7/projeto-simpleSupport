package com.ogl.simpleSupport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "empresa")
@Table(name = "empresa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String emailEmpresa;
    private String razaoSocial;
    private String emailResponsavel;

    public Empresa(String nome, String cnpj, String emailEmpresa, String razaoSocial, String emailResponsavel) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.emailEmpresa = emailEmpresa;
        this.razaoSocial = razaoSocial;
        this.emailResponsavel = emailResponsavel;
    }
}
