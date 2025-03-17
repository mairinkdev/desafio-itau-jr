package com.itau.desafio.service;

import com.itau.desafio.model.Transacao;
import com.itau.desafio.model.Estatistica;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {
    private final ConcurrentMap<OffsetDateTime, Transacao> transacoes = new ConcurrentHashMap<>();

    public void adicionarTransacao(Transacao transacao) {
        if (transacao.getDataHora().isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("A transação não pode ter data futura");
        }
        if (transacao.getValor() < 0) {
            throw new IllegalArgumentException("O valor da transação não pode ser negativo");
        }
        transacoes.put(transacao.getDataHora(), transacao);
    }

    public Estatistica calcularEstatisticas() {
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime limite = agora.minusSeconds(60);

        List<Transacao> transacoesRecentes = transacoes.values().stream()
                .filter(t -> !t.getDataHora().isBefore(limite))
                .collect(Collectors.toList());

        if (transacoesRecentes.isEmpty()) {
            return Estatistica.builder()
                    .count(0)
                    .sum(0.0)
                    .avg(0.0)
                    .min(0.0)
                    .max(0.0)
                    .build();
        }

        return Estatistica.builder()
                .count(transacoesRecentes.size())
                .sum(transacoesRecentes.stream().mapToDouble(Transacao::getValor).sum())
                .avg(transacoesRecentes.stream().mapToDouble(Transacao::getValor).average().orElse(0.0))
                .min(transacoesRecentes.stream().mapToDouble(Transacao::getValor).min().orElse(0.0))
                .max(transacoesRecentes.stream().mapToDouble(Transacao::getValor).max().orElse(0.0))
                .build();
    }

    public void limparTransacoes() {
        transacoes.clear();
    }
} 