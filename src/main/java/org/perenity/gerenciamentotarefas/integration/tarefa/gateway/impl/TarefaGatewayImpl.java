package org.perenity.gerenciamentotarefas.integration.tarefa.gateway.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.tarefa.gateway.TarefaGateway;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.business.tarefa.service.TarefaService;
import org.perenity.gerenciamentotarefas.integration.tarefa.mapper.TarefaEntityMapper;
import org.perenity.gerenciamentotarefas.integration.tarefa.model.TarefaEntity;
import org.perenity.gerenciamentotarefas.integration.tarefa.repository.TarefaRepository;
import org.perenity.gerenciamentotarefas.presentation.tarefa.mapper.TarefaDtoMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TarefaGatewayImpl implements TarefaGateway {

    private final TarefaRepository tarefaRepository;
    private final TarefaEntityMapper tarefaEntityMapper;

    public Optional<Tarefa> buscarTarefa(final Long id){
        return Optional.ofNullable(id)
                .flatMap(tarefaRepository::findById)
                .map(tarefaEntityMapper::toDomain);
    }

    @Override
    public Tarefa cadastrar(final Tarefa tarefa) {
        return Optional.ofNullable(tarefa)
                .map(tarefaEntityMapper::toEntity)
                .map(tarefaRepository::save)
                .map(tarefaEntityMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Tarefa inválida para cadastro"));
    }

    @Override
    public void atualizar(final Long id, final Tarefa tarefa) {
        Optional.ofNullable(tarefa)
                .map(tarefaEntityMapper::toEntity)
                .map(tarefaRepository::save)
                .orElseThrow(() -> new RuntimeException("Tarefa inválida para atualização"));
    }

}
