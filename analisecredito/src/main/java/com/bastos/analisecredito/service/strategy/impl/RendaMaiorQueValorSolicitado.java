package com.bastos.analisecredito.service.strategy.impl;

import com.bastos.analisecredito.domain.Proposta;
import com.bastos.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

@Component
public class RendaMaiorQueValorSolicitado implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return rendaMaiorValorSolicitado(proposta) ? 100 : 0;
    }

    private boolean rendaMaiorValorSolicitado(Proposta proposta) {
        Double renda = proposta.getUsuario().getRenda();
        Double valorSolicitado = proposta.getValorSolicitado();

        return renda > valorSolicitado;
    }
}
