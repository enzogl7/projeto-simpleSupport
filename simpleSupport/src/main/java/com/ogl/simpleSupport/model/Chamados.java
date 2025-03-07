package com.ogl.simpleSupport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "chamados")
@Table(name = "chamados")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chamados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_responsavel")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "usuario_responsavel")
    private User responsavel;

    @ManyToOne
    @JoinColumn(name = "sistema_empresa")
    private SistemasEmpresa sistema;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusChamado status = StatusChamado.EM_ANDAMENTO;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private PrioridadeChamado prioridade = PrioridadeChamado.MEDIA;

    private String titulo;
    private String descricao;
    private LocalDate dataAbertura;
    private LocalDate dataAtualizacao;
    private LocalDate dataFechamento;

}
