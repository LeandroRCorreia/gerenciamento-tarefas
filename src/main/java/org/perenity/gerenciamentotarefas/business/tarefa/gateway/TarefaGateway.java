package org.perenity.gerenciamentotarefas.business.tarefa.gateway;

import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;

import java.util.Optional;

public interface TarefaGateway {
    Optional<Tarefa> buscarTarefa(final Long id);
    Tarefa cadastrar(final Tarefa tarefa);

}
