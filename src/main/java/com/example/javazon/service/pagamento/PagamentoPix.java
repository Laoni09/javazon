package com.example.javazon.service.pagamento;

public class PagamentoPix implements ProcessadorPagamento {
    @Override
    public boolean processar(double valor) {
        return true;
    }
}
