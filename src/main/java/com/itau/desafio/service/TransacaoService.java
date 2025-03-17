package com.itau.desafio.service;

import com.itau.desafio.model.Transacao;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
} 