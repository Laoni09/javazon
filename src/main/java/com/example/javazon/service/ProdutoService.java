package com.example.javazon.service;

import com.example.javazon.domain.Produto;
import com.example.javazon.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProdutoService {
    private final EstoqueRepository repository;

    public ProdutoService(EstoqueRepository repository) {
        this.repository = repository;
    }

    public Collection<Produto> listarTodos() {
        return repository.listarTodos();
    }

    public Produto buscarPorId(Long id) {
        return repository.buscarProdutoPorId(id);
    }

    public void adicionar(Produto produto) {
        repository.adicionarProduto(produto);
    }

    public void remover(Long id) {
        repository.removerProduto(id);
    }
}
