package com.bastos.analisecredito.service.strategy;

import com.bastos.analisecredito.domain.Proposta;

public interface CalculoPonto {

    int calcular(Proposta proposta);
}
