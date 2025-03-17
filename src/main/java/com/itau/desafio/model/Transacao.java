package com.itau.desafio.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {
    @NotNull(message = "O valor não pode ser nulo")
    @PositiveOrZero(message = "O valor não pode ser negativo")
    private Double valor;

    @NotNull(message = "A data e hora não podem ser nulas")
    private OffsetDateTime dataHora;
} 