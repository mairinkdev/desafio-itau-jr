package com.itau.desafio.controller;

import com.itau.desafio.model.Transacao;
import com.itau.desafio.model.Estatistica;
import com.itau.desafio.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Transações", description = "API para gerenciar transações e estatísticas")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/transacao")
    @Operation(summary = "Criar uma nova transação", description = "Registra uma nova transação no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
        @ApiResponse(responseCode = "422", description = "Dados da transação inválidos"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Void> criarTransacao(@Valid @RequestBody Transacao transacao) {
        transacaoService.adicionarTransacao(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/estatistica")
    @Operation(summary = "Obter estatísticas", description = "Retorna estatísticas das transações dos últimos 60 segundos")
    @ApiResponse(responseCode = "200", description = "Estatísticas calculadas com sucesso")
    public Estatistica obterEstatisticas() {
        return transacaoService.calcularEstatisticas();
    }

    @DeleteMapping("/transacao")
    @Operation(summary = "Limpar transações", description = "Remove todas as transações do sistema")
    @ApiResponse(responseCode = "204", description = "Transações removidas com sucesso")
    public ResponseEntity<Void> limparTransacoes() {
        transacaoService.limparTransacoes();
        return ResponseEntity.noContent().build();
    }
} 