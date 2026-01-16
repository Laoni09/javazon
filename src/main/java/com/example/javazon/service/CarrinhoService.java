package com.example.javazon.service;

import com.example.javazon.domain.CarrinhoDeCompras;
import com.example.javazon.domain.ItemCarrinho;
import com.example.javazon.domain.Produto;
import com.example.javazon.exception.RecursoNaoEncontradoException;
import com.example.javazon.repository.EstoqueRepository;
import com.example.javazon.service.pagamento.PagamentoCartao;
import com.example.javazon.service.pagamento.PagamentoPix;
import com.example.javazon.service.pagamento.ProcessadorPagamento;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CarrinhoService {
    private final CarrinhoDeCompras carrinho = new CarrinhoDeCompras();

    private final EstoqueRepository repository;
    private final PedidoService pedidoService;

    public CarrinhoService(EstoqueRepository Repository, PedidoService pedidoService) {
        this.repository = Repository;
        this.pedidoService = pedidoService;
    }

    public Collection<ItemCarrinho> listarItens() {
        return carrinho.getItens();
    }

    public void adicionarItem(Long produtoId, int quantidade) {
        Produto produto = repository.buscarProdutoPorId(produtoId);

        if (produto == null) {
            throw new RecursoNaoEncontradoException("Produto com ID " + produtoId + " não existe no catálogo.");
        }

        carrinho.adicionarItem(produto, quantidade);
    }

    public void removerItem(Long produtoId) {
        carrinho.removerItem(produtoId);
    }

    public String finalizarCompra(String metodoPagamento) {
        ProcessadorPagamento processador;

        // Factory simples para escolher o pagamento
        if ("PIX".equalsIgnoreCase(metodoPagamento)) {
            processador = new PagamentoPix();
        } else if ("CARTAO".equalsIgnoreCase(metodoPagamento)) {
            processador = new PagamentoCartao("1234-5678-9012-3456"); // Mockado
        } else {
            return "Método de pagamento inválido. Use PIX ou CARTAO.";
        }

        // Delega para o PedidoService fazer a baixa de estoque
        pedidoService.finalizarPedido(carrinho, processador);

        return "Compra finalizada com sucesso usando " + metodoPagamento;
    }
}
