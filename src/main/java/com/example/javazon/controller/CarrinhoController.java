package com.example.javazon.controller;

import com.example.javazon.domain.ItemCarrinho;
import com.example.javazon.service.CarrinhoService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {
    private final CarrinhoService service;

    public CarrinhoController(CarrinhoService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<ItemCarrinho> listar() {
        return service.listarItens();
    }

    @PostMapping("/adicionar/{id}")
    public String adicionar(@PathVariable Long id,
                            @RequestParam(defaultValue = "1") int quantidade) {
        service.adicionarItem(id, quantidade);
        return "Item adicionado ao carrinho!";
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable Long id) {
        service.removerItem(id);
        return "Item removido do carrinho!";
    }

    @PostMapping("/finalizar")
    public String finalizar(@RequestBody String metodoPagamento) {
        return service.finalizarCompra(metodoPagamento);
    }
}
