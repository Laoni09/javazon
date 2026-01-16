package com.example.javazon.repository;

import com.example.javazon.exception.EstoqueInsuficienteException;
import com.example.javazon.domain.Produto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EstoqueRepository {
    private final Map<Long, Produto> catalogo;

    private long idSequence = 1;

    public EstoqueRepository() { this.catalogo = new HashMap<>(); }

    public Produto buscarProdutoPorId(Long id) {
        return catalogo.get(id);
    }

    public void adicionarProduto(Produto produto) {
        produto.setId(idSequence++);
        catalogo.put(produto.getId(), produto);
        System.out.println(produto.getNome() + " adicionado ao estoque com sucesso!");
    }

    public void removerProdutoQuantidade(Long id, int quantidade) {
        Produto produto = catalogo.get(id);

        if (produto == null) {
            System.out.println("Produto não encontrado!");
            return;
        } else if (produto.getQuantidadeEmEstoque() < quantidade) {
            throw new EstoqueInsuficienteException("Sem estoque suficiente");
        }

        produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - quantidade);
        System.out.println(produto.getNome() + " removido em " + quantidade + " unidades!");
    }

    public java.util.Collection<Produto> listarTodos() {
        return catalogo.values();
    }

    public void removerProduto(Long id) {
        if (catalogo.containsKey(id)) {
            catalogo.remove(id);
            System.out.println("Produto ID " + id + " excluído do sistema.");
        } else {
            System.out.println("Produto não encontrado para exclusão.");
        }
    }
}
