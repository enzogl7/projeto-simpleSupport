package com.ogl.simpleSupport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "convite_funcionario")
@Table(name = "convite_funcionario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConviteFuncionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailFuncionario;

    private String tokenConvite;

    private boolean aceito;

    @ManyToOne
    private Empresa empresaResponsavel;

    private LocalDateTime dataExpiracao;
}
