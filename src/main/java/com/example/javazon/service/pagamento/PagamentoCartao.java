package com.example.javazon.service.pagamento;

public class PagamentoCartao implements ProcessadorPagamento {
    private String numeroCartao;

    public PagamentoCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    @Override
    public boolean processar(double valor) {
        System.out.println("--- Processando Cartão de Crédito ---");

        return numeroCartao != null && numeroCartao.length() == 16;
    }
}
