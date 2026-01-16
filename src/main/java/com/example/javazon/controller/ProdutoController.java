package com.example.javazon.controller;

import com.example.javazon.domain.Produto;
import com.example.javazon.domain.ProdutoDigital;
import com.example.javazon.domain.ProdutoFisico;
import com.example.javazon.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Produto> listarTodos() {
        // Você precisará criar o método 'listarTodos()' no Service também para repassar a chamada
        return service.listarTodos();
    }

    @GetMapping("/{id}") // URL: GET /produtos/10
    public Produto buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping("/fisico")
    public String adicionarFisico(@RequestBody ProdutoFisico produto) {
        service.adicionar(produto);
        return "Produto Físico " + produto.getNome() + " adicionado!";
    }

    @PostMapping("/digital")
    public String adicionarDigital(@RequestBody ProdutoDigital produto) {
        service.adicionar(produto);
        return "Produto Digital " + produto.getNome() + " adicionado!";
    }
    @DeleteMapping("/{id}")
    public String remover(@PathVariable Long id) {
        service.remover(id);
        return "Produto removido com sucesso!";
    }
}
