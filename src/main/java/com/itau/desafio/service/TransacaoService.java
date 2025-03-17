package com.itau.desafio.service;

import com.itau.desafio.model.Transacao;
import com.itau.desafio.model.Estatistica;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TransacaoService {
    private final ConcurrentMap<OffsetDateTime, Transacao> transacoes = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public TransacaoService() {
        // Agenda limpeza de transações antigas a cada minuto
        scheduler.scheduleAtFixedRate(this::removerTransacoesAntigas, 1, 1, TimeUnit.MINUTES);
    }

    public void adicionarTransacao(Transacao transacao) {
        OffsetDateTime agora = OffsetDateTime.now();
        if (transacao.getDataHora().isAfter(agora)) {
            throw new IllegalArgumentException("A transação não pode ter data futura");
        }
        if (transacao.getValor() < 0) {
            throw new IllegalArgumentException("O valor da transação não pode ser negativo");
        }
        transacoes.put(transacao.getDataHora(), transacao);
    }

    public Estatistica calcularEstatisticas() {
        OffsetDateTime limite = OffsetDateTime.now().minusSeconds(60);
        
        // Usa DoubleSummaryStatistics para calcular todas as estatísticas em uma única passagem
        DoubleSummaryStatistics stats = transacoes.values().stream()
                .filter(t -> !t.getDataHora().isBefore(limite))
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();
        
        if (stats.getCount() == 0) {
            return Estatistica.builder()
                    .count(0)
                    .sum(0.0)
                    .avg(0.0)
                    .min(0.0)
                    .max(0.0)
                    .build();
        }
        
        return Estatistica.builder()
                .count(stats.getCount())
                .sum(stats.getSum())
                .avg(stats.getAverage())
                .min(stats.getMin())
                .max(stats.getMax())
                .build();
    }

    public void limparTransacoes() {
        transacoes.clear();
    }
    
    private void removerTransacoesAntigas() {
        OffsetDateTime limite = OffsetDateTime.now().minusMinutes(10);
        transacoes.entrySet().removeIf(entry -> entry.getKey().isBefore(limite));
    }
} 