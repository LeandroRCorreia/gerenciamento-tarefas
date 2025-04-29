package org.perenity.gerenciamentotarefas.business.tarefa.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.pessoa.gateway.PessoaGateway;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.business.tarefa.gateway.TarefaGateway;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.business.tarefa.service.TarefaService;
import org.perenity.gerenciamentotarefas.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarefaServiceImpl implements TarefaService {

    private final TarefaGateway tarefaGateway;
    private final PessoaGateway pessoaGateway;

    @Override
    public Tarefa cadastrarTarefa(final Tarefa tarefa) {
        return tarefaGateway.cadastrar(tarefa);
    }

    @Override
    public void alocarTarefaPessoa(final Long id, final Tarefa tarefa) {
        final Tarefa tarefaEncontrada = tarefaGateway
                .buscarTarefa(id)
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrada com ID: " + id));

        final Pessoa pessoaEncontrada = pessoaGateway
                .buscarPessoa(tarefa.getPessoaId())
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada com ID: " + tarefa.getPessoaId()));

        //TODO: Alinhamento do campo departamento
        if (!tarefaEncontrada.getDepartamento().equalsIgnoreCase(pessoaEncontrada.getDepartamento())) {
            throw new IllegalArgumentException("Departamento da tarefa e da pessoa não são compatíveis");
        }

        final Tarefa tarefaComPessoaAlocada = tarefaEncontrada
                .toBuilder()
                .pessoaId(tarefa.getPessoaId())
                .build();

        tarefaGateway.atualizar(id, tarefaComPessoaAlocada);
    }
}
