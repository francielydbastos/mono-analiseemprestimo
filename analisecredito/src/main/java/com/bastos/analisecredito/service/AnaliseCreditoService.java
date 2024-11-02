package com.bastos.analisecredito.service;

import com.bastos.analisecredito.domain.Proposta;
import com.bastos.analisecredito.exceptions.StrategyException;
import com.bastos.analisecredito.service.strategy.CalculoPonto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    @Autowired
    private List<CalculoPonto> calculoPontoList;
    @Autowired
    private NotificacaoRabbitService notificacaoRabbitService;
    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    public void analisar(Proposta proposta) {

        try {
            int score = calculoPontoList.stream().mapToInt(impl -> impl.calcular(proposta)).sum();
            boolean aprovada = score > 350;

            proposta.setAprovada(aprovada);
            proposta.setObservacao("Proposta aprovada.");
        } catch (StrategyException e) {
            proposta.setAprovada(false);
            proposta.setObservacao(e.getMessage());
        }

        notificacaoRabbitService.notificar(exchangePropostaConcluida, proposta);
    }
}
