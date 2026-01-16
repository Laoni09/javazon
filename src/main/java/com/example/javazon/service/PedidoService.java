package com.example.javazon.service;


import com.example.javazon.domain.CarrinhoDeCompras;
import com.example.javazon.domain.ItemCarrinho;
import com.example.javazon.exception.EstoqueInsuficienteException;
import com.example.javazon.repository.EstoqueRepository;
import com.example.javazon.service.pagamento.ProcessadorPagamento;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    private final EstoqueRepository estoque;

    public PedidoService(EstoqueRepository estoque) {
        this.estoque = estoque;
    }

    public void finalizarPedido(CarrinhoDeCompras carrinho, ProcessadorPagamento metodoPagamento) {
        if (carrinho.carrinhoIsEmpty()) {
            return;
        }


        double valorTotal = calcularValorTotal(carrinho);

        boolean pagamentoAprovado = metodoPagamento.processar(valorTotal);

        if (pagamentoAprovado) {
            baixarEstoque(carrinho);
            carrinho.esvaziarCarrinho();
        } else {
            System.out.println("❌ Pagamento Recusado. O estoque não foi alterado.");
        }
    }

    private double calcularValorTotal(CarrinhoDeCompras carrinho) {
        double freteTotal = 0;
        for (ItemCarrinho item : carrinho.getItens()) {
            freteTotal += item.getProduto().calcularFrete();
        }
        return freteTotal + carrinho.gerarTotal();
    }

    private void baixarEstoque(CarrinhoDeCompras carrinho) {
        for (ItemCarrinho item : carrinho.getItens()) {
            estoque.removerProdutoQuantidade(item.getProduto().getId(), item.getQuantidade()) ;
        }
    }
}
