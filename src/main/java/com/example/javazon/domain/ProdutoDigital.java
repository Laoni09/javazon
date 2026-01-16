package com.example.javazon.domain;

public class ProdutoDigital extends Produto{
    private String url;
    private double tamanhoDoArquivo;

    public ProdutoDigital(String nome, double preco, int quantidadeEmEstoque, String url, double tamanhoDoArquivo) {
        super(nome, preco, quantidadeEmEstoque);
        this.url = url;
        this.tamanhoDoArquivo = tamanhoDoArquivo;
    }

    @Override
    public double calcularFrete() {
        return 0.0;
    }
}
