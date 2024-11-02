package com.bastos.proposta_app.listener;

import com.bastos.proposta_app.dto.PropostaResponseDto;
import com.bastos.proposta_app.entity.Proposta;
import com.bastos.proposta_app.mapper.PropostaMapper;
import com.bastos.proposta_app.repository.PropostaRepository;
import com.bastos.proposta_app.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        propostaRepository.atualizarProposta(proposta.getId(), proposta.getAprovada(), proposta.getObservacao());

        PropostaResponseDto propostaResponseDto = PropostaMapper.INSTANCE.convertEntityToDto(proposta);

        webSocketService.notificar(propostaResponseDto);
    }
}
