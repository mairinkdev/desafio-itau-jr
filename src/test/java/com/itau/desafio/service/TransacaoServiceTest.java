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
        // Arrange
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now().minusMinutes(5));

        // Act & Assert
        assertDoesNotThrow(() -> transacaoService.adicionarTransacao(transacao));
    }

    @Test
    void deveRejeitarTransacaoComDataFutura() {
        // Arrange
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now().plusMinutes(5));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transacaoService.adicionarTransacao(transacao));
    }

    @Test
    void deveRejeitarTransacaoComValorNegativo() {
        // Arrange
        Transacao transacao = new Transacao(-100.0, OffsetDateTime.now().minusMinutes(5));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> transacaoService.adicionarTransacao(transacao));
    }

    @Test
    void deveRetornarEstatisticasCorretas() {
        // Arrange
        transacaoService.limparTransacoes();
        OffsetDateTime agora = OffsetDateTime.now();
        
        transacaoService.adicionarTransacao(new Transacao(100.0, agora.minusSeconds(10)));
        transacaoService.adicionarTransacao(new Transacao(200.0, agora.minusSeconds(20)));
        transacaoService.adicionarTransacao(new Transacao(300.0, agora.minusSeconds(30)));
        
        // Transação fora do período de 60 segundos
        transacaoService.adicionarTransacao(new Transacao(1000.0, agora.minusSeconds(61)));

        // Act
        Estatistica estatistica = transacaoService.calcularEstatisticas();

        // Assert
        assertEquals(3, estatistica.getCount());
        assertEquals(600.0, estatistica.getSum());
        assertEquals(200.0, estatistica.getAvg());
        assertEquals(100.0, estatistica.getMin());
        assertEquals(300.0, estatistica.getMax());
    }

    @Test
    void deveRetornarEstatisticasZeradasQuandoNaoHaTransacoes() {
        // Arrange
        transacaoService.limparTransacoes();

        // Act
        Estatistica estatistica = transacaoService.calcularEstatisticas();

        // Assert
        assertEquals(0, estatistica.getCount());
        assertEquals(0.0, estatistica.getSum());
        assertEquals(0.0, estatistica.getAvg());
        assertEquals(0.0, estatistica.getMin());
        assertEquals(0.0, estatistica.getMax());
    }

    @Test
    void deveLimparTodasTransacoes() {
        // Arrange
        transacaoService.adicionarTransacao(new Transacao(100.0, OffsetDateTime.now().minusMinutes(5)));
        
        // Act
        transacaoService.limparTransacoes();
        Estatistica estatistica = transacaoService.calcularEstatisticas();
        
        // Assert
        assertEquals(0, estatistica.getCount());
    }
} 