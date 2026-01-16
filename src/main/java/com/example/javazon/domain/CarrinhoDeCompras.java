package com.example.javazon.domain;

import com.example.javazon.exception.EstoqueInsuficienteException;
import com.example.javazon.exception.RecursoNaoEncontradoException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class CarrinhoDeCompras {
    private List<ItemCarrinho> itens;

    public CarrinhoDeCompras() { this.itens = new ArrayList<>(); }

    public void adicionarItem(Produto produto, int quantidade) {
        Optional<ItemCarrinho> itemExistente = itens.stream()
                .filter(item -> item.getProduto().getId().equals(produto.getId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrinho item = itemExistente.get();
            if(item.getProduto().getQuantidadeEmEstoque() < item.getQuantidade() + quantidade) {
                throw new EstoqueInsuficienteException("Sem estoque suficiente");
            } else {
                item.setQuantidade(item.getQuantidade() + quantidade);
            }
        } else {
            ItemCarrinho novoItem = new ItemCarrinho(produto, quantidade);
            if(novoItem.getProduto().getQuantidadeEmEstoque() < novoItem.getQuantidade()) {
                throw new EstoqueInsuficienteException("Sem estoque suficiente");
            } else {
                itens.add(novoItem);
            }
        }
    }

    public void removerItem(Long id) {
        boolean removeu = itens.removeIf(itemCarrinho -> itemCarrinho.getProduto().getId().equals(id));

        if (!removeu) {
            throw new RecursoNaoEncontradoException("Produto não encontrado no carrinho");
        }
    }

    public double gerarTotal() {
        return itens.stream()
                .mapToDouble(ItemCarrinho::calcularSubtotal)
                .sum();
    }

    public boolean carrinhoIsEmpty() {
        return itens.isEmpty();
    }

    public void esvaziarCarrinho() {
        itens.clear();
    }
}
