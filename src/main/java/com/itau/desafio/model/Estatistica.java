package com.itau.desafio.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Estatistica {
    private long count;
    private double sum;
    private double avg;
    private double min;
    private double max;
} 