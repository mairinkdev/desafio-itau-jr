package com.itau.desafio.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class Transacao {
    @NotNull(message = "O valor é obrigatório")
    @PositiveOrZero(message = "O valor deve ser maior ou igual a zero")
    private Double valor;

    @NotNull(message = "A data/hora é obrigatória")
    private OffsetDateTime dataHora;
} 