package org.perenity.gerenciamentotarefas.business.tarefa.service;

import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;

public interface TarefaService {

    Tarefa cadastrarTarefa(final Tarefa tarefa);
    void alocarTarefaPessoa(final Long id, final Tarefa tarefa);
    void finalizarTarefa(final Long id, final Tarefa tarefa);
}
