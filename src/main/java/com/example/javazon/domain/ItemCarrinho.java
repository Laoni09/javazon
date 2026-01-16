package com.example.javazon.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ItemCarrinho {
    private final Produto produto;
    @Setter
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double calcularSubtotal() {
        return this.produto.getPreco() * this.quantidade;
    }
}
