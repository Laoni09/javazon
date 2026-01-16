package com.example.javazon.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Produto {
    @Setter
    private Long id;
    private String nome;
    private double preco;
    @Setter
    private int quantidadeEmEstoque;

    public Produto(String nome, double preco, int quantidadeEmEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public abstract double calcularFrete();
}
