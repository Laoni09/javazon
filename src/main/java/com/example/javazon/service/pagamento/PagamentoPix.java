package com.example.javazon.service.pagamento;

public class PagamentoPix implements ProcessadorPagamento {
    @Override
    public boolean processar(double valor) {
        double valorComDesconto = valor * 0.95;
        return true;
    }
}
