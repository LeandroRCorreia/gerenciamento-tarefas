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

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarefaServiceImpl implements TarefaService {

    private final TarefaGateway tarefaGateway;
    private final PessoaGateway pessoaGateway;

    @Override
    public Collection<Tarefa> listarTarefasPendentes() {
        log.info("[Tarefa-Api][listarTarefasPendentes] Listando tarefas pendentes");
        return tarefaGateway.listarTarefasPendentes();
    }

    @Override
    public Tarefa cadastrarTarefa(final Tarefa tarefa) {
        log.info("[Tarefa-Api][cadastrarTarefa] Cadastrando nova tarefa: {}", tarefa);
        return tarefaGateway.cadastrar(tarefa);
    }

    @Override
    public void alocarTarefaPessoa(final Long id, final Tarefa tarefa) {
        log.info("[Tarefa-Api][alocarTarefaPessoa] Iniciando alocação da tarefa com ID {} para a pessoa com ID {}", id, tarefa.getPessoaId());

        final Tarefa tarefaEncontrada = buscarTarefaOuFalhar(id);
        final Pessoa pessoaEncontrada = buscarPessoaOuFalhar(tarefa.getPessoaId());

        if (!tarefaEncontrada.getDepartamento().equalsIgnoreCase(pessoaEncontrada.getDepartamento())) {
            log.warn("[Tarefa-Api][alocarTarefaPessoa] Departamento da tarefa ({}) é diferente do departamento da pessoa ({})",
                    tarefaEncontrada.getDepartamento(), pessoaEncontrada.getDepartamento());
            throw new IllegalArgumentException("Departamento da tarefa e da pessoa não são compatíveis");
        }

        final Tarefa tarefaComPessoaAlocada = tarefaEncontrada
                .toBuilder()
                .pessoaId(tarefa.getPessoaId())
                .build();

        tarefaGateway.atualizar(id, tarefaComPessoaAlocada);
        log.info("[Tarefa-Api][alocarTarefaPessoa] Tarefa com ID {} alocada para a pessoa com ID {}", id, tarefa.getPessoaId());
    }

    @Override
    public void finalizarTarefa(final Long id, final Tarefa tarefa) {
        log.info("[Tarefa-Api][finalizarTarefa] Finalizando tarefa com ID {} para pessoa ID {}", id, tarefa.getPessoaId());

        final Tarefa tarefaEncontrada = buscarTarefaOuFalhar(id);
        buscarPessoaOuFalhar(tarefa.getPessoaId());

        final Tarefa tarefaFinalizada = tarefaEncontrada.toBuilder()
                .finalizado(Boolean.TRUE)
                .duracaoHoras(tarefa.getDuracaoHoras())
                .build();

        tarefaGateway.atualizar(id, tarefaFinalizada);
        log.info("[Tarefa-Api][finalizarTarefa] Tarefa com ID {} finalizada com sucesso. Duração registrada: {} horas", id, tarefa.getDuracaoHoras());
    }

    private Tarefa buscarTarefaOuFalhar(Long tarefaId) {
        return tarefaGateway
                .buscarTarefa(tarefaId)
                .orElseThrow(() -> {
                    log.warn("[Tarefa-Api][buscarTarefaOuFalhar] Tarefa com ID {} não encontrada", tarefaId);
                    return new NotFoundException("Tarefa não encontrada com ID: " + tarefaId);
                });
    }

    private Pessoa buscarPessoaOuFalhar(Long pessoaId) {
        return pessoaGateway
                .buscarPessoa(pessoaId)
                .orElseThrow(() -> {
                    log.warn("[Tarefa-Api][buscarPessoaOuFalhar] Pessoa com ID {} não encontrada", pessoaId);
                    return new NotFoundException("Pessoa não encontrada com ID: " + pessoaId);
                });
    }

}
