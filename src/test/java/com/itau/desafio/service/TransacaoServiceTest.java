package com.itau.desafio.service;

import com.itau.desafio.model.Transacao;
import com.itau.desafio.model.Estatistica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TransacaoServiceTest {
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        transacaoService = new TransacaoService();
    }

    @Test
    void deveAdicionarTransacaoValida() {
        Transacao transacao = new Transacao();
        transacao.setValor(100.0);
        transacao.setDataHora(OffsetDateTime.now().minusMinutes(1));

        assertDoesNotThrow(() -> transacaoService.adicionarTransacao(transacao));
    }

    @Test
    void deveRejeitarTransacaoComDataFutura() {
        Transacao transacao = new Transacao();
        transacao.setValor(100.0);
        transacao.setDataHora(OffsetDateTime.now().plusMinutes(1));

        assertThrows(IllegalArgumentException.class, () -> transacaoService.adicionarTransacao(transacao));
    }

    @Test
    void deveRejeitarTransacaoComValorNegativo() {
        Transacao transacao = new Transacao();
        transacao.setValor(-100.0);
        transacao.setDataHora(OffsetDateTime.now().minusMinutes(1));

        assertThrows(IllegalArgumentException.class, () -> transacaoService.adicionarTransacao(transacao));
    }

    @Test
    void deveLimparTodasAsTransacoes() {
        Transacao transacao = new Transacao();
        transacao.setValor(100.0);
        transacao.setDataHora(OffsetDateTime.now().minusMinutes(1));
        transacaoService.adicionarTransacao(transacao);

        transacaoService.limparTransacoes();
        Estatistica estatistica = transacaoService.calcularEstatisticas();
        
        assertEquals(0, estatistica.getCount());
        assertEquals(0.0, estatistica.getSum());
        assertEquals(0.0, estatistica.getAvg());
        assertEquals(0.0, estatistica.getMin());
        assertEquals(0.0, estatistica.getMax());
    }

    @Test
    void deveCalcularEstatisticasCorretamente() {
        // Adiciona transações com diferentes valores
        Transacao t1 = new Transacao();
        t1.setValor(100.0);
        t1.setDataHora(OffsetDateTime.now().minusSeconds(30));
        transacaoService.adicionarTransacao(t1);

        Transacao t2 = new Transacao();
        t2.setValor(200.0);
        t2.setDataHora(OffsetDateTime.now().minusSeconds(20));
        transacaoService.adicionarTransacao(t2);

        Transacao t3 = new Transacao();
        t3.setValor(300.0);
        t3.setDataHora(OffsetDateTime.now().minusSeconds(10));
        transacaoService.adicionarTransacao(t3);

        Estatistica estatistica = transacaoService.calcularEstatisticas();

        assertEquals(3, estatistica.getCount());
        assertEquals(600.0, estatistica.getSum());
        assertEquals(200.0, estatistica.getAvg());
        assertEquals(100.0, estatistica.getMin());
        assertEquals(300.0, estatistica.getMax());
    }

    @Test
    void deveIgnorarTransacoesAntigasNoCalculoDeEstatisticas() {
        // Adiciona transação antiga (mais de 60 segundos)
        Transacao t1 = new Transacao();
        t1.setValor(100.0);
        t1.setDataHora(OffsetDateTime.now().minusMinutes(2));
        transacaoService.adicionarTransacao(t1);

        // Adiciona transação recente
        Transacao t2 = new Transacao();
        t2.setValor(200.0);
        t2.setDataHora(OffsetDateTime.now().minusSeconds(30));
        transacaoService.adicionarTransacao(t2);

        Estatistica estatistica = transacaoService.calcularEstatisticas();

        assertEquals(1, estatistica.getCount());
        assertEquals(200.0, estatistica.getSum());
        assertEquals(200.0, estatistica.getAvg());
        assertEquals(200.0, estatistica.getMin());
        assertEquals(200.0, estatistica.getMax());
    }
} 