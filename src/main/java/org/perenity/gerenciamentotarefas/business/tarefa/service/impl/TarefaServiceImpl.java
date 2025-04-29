package org.perenity.gerenciamentotarefas.business.tarefa.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.tarefa.gateway.TarefaGateway;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.business.tarefa.service.TarefaService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarefaServiceImpl implements TarefaService {

    private final TarefaGateway tarefaGateway;

    @Override
    public Tarefa cadastrarTarefa(final Tarefa tarefa) {
        return tarefaGateway.cadastrar(tarefa);
    }

}
