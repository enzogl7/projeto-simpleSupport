package com.ogl.simpleSupport.model;

public enum PrioridadeChamado {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
    IMEDIATA("Imediata"),;

    private final String descricao;

    PrioridadeChamado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

