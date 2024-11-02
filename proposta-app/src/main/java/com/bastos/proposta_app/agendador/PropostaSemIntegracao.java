package com.bastos.proposta_app.agendador;

import com.bastos.proposta_app.dto.PropostaResponseDto;
import com.bastos.proposta_app.mapper.PropostaMapper;
import com.bastos.proposta_app.repository.PropostaRepository;
import com.bastos.proposta_app.service.NotificacaoRabbitService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;
    private final NotificacaoRabbitService notificacaoRabbitService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao() {
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoRabbitService.notificar(proposta, exchange);
                propostaRepository.atualizarStatusIntegrada(proposta.getId(), true);
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }
}
