package com.example.javazon.domain;

public class ProdutoFisico extends Produto{
    private final double peso;
    private final double volume;

    public ProdutoFisico(String nome, double preco, int quantidadeEmEstoque, double peso, double volume) {
        super(nome, preco, quantidadeEmEstoque);
        this.peso = peso;
        this.volume = volume;
    }

    @Override
    public double calcularFrete() {
        return this.peso*this.volume;
    }
}
