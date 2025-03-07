package com.ogl.simpleSupport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "sistemas_empresa")
@Table(name = "sistemas_empresa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SistemasEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "empresa")
    private Empresa empresa;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private String versao;
    private String categoria;
    private LocalDateTime createdAt = LocalDateTime.now();

}
