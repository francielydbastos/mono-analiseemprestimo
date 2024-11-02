package com.bastos.proposta_app.service;

import com.bastos.proposta_app.dto.PropostaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    public void notificar(PropostaResponseDto propostaResponseDto) {
        template.convertAndSend("/propostas", propostaResponseDto);
    }
}