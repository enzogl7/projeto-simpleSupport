package com.ogl.simpleSupport.model;

public enum StatusChamado {
    EM_ANDAMENTO("Em Andamento"),
    AGUARDANDO_CLIENTE("Aguardando cliente"),
    RESOLVIDO("Resolvido"),
    REABERTO("Reaberto"),
    PENDENTE("Pendente"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusChamado(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
