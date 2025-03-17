package com.itau.desafio.controller;

import com.itau.desafio.model.Transacao;
import com.itau.desafio.model.Estatistica;
import com.itau.desafio.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/transacao")
    public ResponseEntity<Void> criarTransacao(@Valid @RequestBody Transacao transacao) {
        try {
            transacaoService.adicionarTransacao(transacao);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping("/estatistica")
    public ResponseEntity<Estatistica> obterEstatisticas() {
        return ResponseEntity.ok(transacaoService.calcularEstatisticas());
    }
} 