package org.perenity.gerenciamentotarefas.integration.tarefa.gateway.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.tarefa.gateway.TarefaGateway;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.integration.tarefa.mapper.TarefaEntityMapper;
import org.perenity.gerenciamentotarefas.integration.tarefa.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TarefaGatewayImpl implements TarefaGateway {

    private final TarefaRepository tarefaRepository;
    private final TarefaEntityMapper tarefaEntityMapper;

    @Override
    public Collection<Tarefa> listarTarefasPorPessoaEPeriodo(
            final Long pessoaId,
            final LocalDateTime inicio,
            final LocalDateTime fim) {
        return tarefaRepository
                .findByPessoaIdAndPrazoBetween(pessoaId, inicio, fim)
                .stream()
                .map(tarefaEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Collection<Tarefa> listarTarefasPendentes() {
        return tarefaRepository.findTop3ByPessoaIdIsNullOrderByPrazoAsc()
                .stream()
                .map(tarefaEntityMapper::toDomain)
                .toList();
    }

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
