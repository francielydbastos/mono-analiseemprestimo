package com.bastos.notificacao.listener;

import com.bastos.notificacao.constante.MensagemConstante;
import com.bastos.notificacao.domain.Proposta;
import com.bastos.notificacao.service.NotificacaoSnsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private NotificacaoSnsService notificacaoSnsService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaPendente(Proposta proposta) {
        String mensagemConstante = proposta.getAprovada() ? MensagemConstante.PROPOSTA_APROVADA : MensagemConstante.PROPOSTA_NEGADA;
        String mensagem = String.format(mensagemConstante, proposta.getUsuario().getNome(), proposta.getObservacao());
        notificacaoSnsService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
