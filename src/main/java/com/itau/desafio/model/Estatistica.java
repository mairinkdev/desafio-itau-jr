package com.itau.desafio.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Estatistica {
    long count;
    double sum;
    double avg;
    double min;
    double max;
} 