package org.perenity.gerenciamentotarefas.business.tarefa.gateway;

import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface TarefaGateway {
    Collection<Tarefa> listarTarefasPorPessoaEPeriodo(
            final Long pessoaId,
            final LocalDateTime inicio,
            final LocalDateTime fim);
    Collection<Tarefa> listarTarefasPendentes();
    Optional<Tarefa> buscarTarefa(final Long id);
    Tarefa cadastrar(final Tarefa tarefa);
    void atualizar(final Long id, final Tarefa tarefa);

}
