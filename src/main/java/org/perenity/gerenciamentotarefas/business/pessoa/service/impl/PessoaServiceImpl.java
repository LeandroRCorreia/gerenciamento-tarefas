package org.perenity.gerenciamentotarefas.business.pessoa.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.pessoa.gateway.PessoaGateway;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.business.pessoa.service.PessoaService;
import org.perenity.gerenciamentotarefas.business.tarefa.gateway.TarefaGateway;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.exception.NotFoundException;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasNomePeriodo;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasTotalHoraTarefa;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PessoaServiceImpl implements PessoaService {

    private final PessoaGateway pessoaGateway;
    private final TarefaGateway tarefaGateway;

    @Override
    public Collection<ResponseListarPessoasNomePeriodo> listarPessoasPorNomePeriodo(
            final String nome,
            final LocalDateTime inicio,
            final LocalDateTime fim) {
        final Collection<Pessoa> pessoas = pessoaGateway.listarPessoasPorNomePeriodo(nome, inicio, fim);

        return pessoas.stream().map(pessoa -> {
            final Collection<Tarefa> tarefas = tarefaGateway
                    .listarTarefasPorPessoaEPeriodo(pessoa.getId(), inicio, fim);

            final Double mediaHoras = pessoa.calcularMediaHorasGastas(tarefas);

            return ResponseListarPessoasNomePeriodo.builder()
                    .pessoaId(pessoa.getId())
                    .nome(pessoa.getNome())
                    .departamento(pessoa.getDepartamento())
                    .mediaHorasGastaPorTarefa(mediaHoras)
                    .build();
        }).toList();
    }

    @Override
    public Collection<ResponseListarPessoasTotalHoraTarefa> listarPessoasComTotalHorasGastas() {
        final Collection<Pessoa> pessoas = pessoaGateway.listarTodas();

        return pessoas.stream().map(pessoa -> {
            final Collection<Tarefa> tarefas = tarefaGateway.listarTarefasPorPessoa(pessoa.getId());

            final Long totalHoras = tarefas.stream()
                    .filter(t -> Boolean.TRUE.equals(t.getFinalizado()) && t.getDuracaoHoras() != null)
                    .mapToLong(Tarefa::getDuracaoHoras)
                    .sum();

            return ResponseListarPessoasTotalHoraTarefa.builder()
                    .pessoaId(pessoa.getId())
                    .nome(pessoa.getNome())
                    .departamento(pessoa.getDepartamento())
                    .totalHorasGastas(totalHoras)
                    .build();
        }).toList();
    }

    @Override
    public Pessoa cadastrarPessoa(final Pessoa pessoa) {
        return Optional.ofNullable(pessoa)
                .map(pessoaGateway::cadastrar)
                .map(pessoaCadastrada -> {
                    log.info("[Pessoa-Api][cadastrarPessoa] Pessoa cadastrada com sucesso! ID: {}", pessoaCadastrada.getId());
                    return pessoaCadastrada;
                })
                .orElseThrow();
    }

    @Override
    public void atualizarPessoa(final Long id, final Pessoa pessoa) {
        Optional.ofNullable(pessoa)
                .ifPresent(p -> {
                    final Pessoa pessoaEncontrada = pessoaGateway
                            .buscarPessoa(id)
                            .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
                    final Pessoa buildPessoaAtualizada = buildPessoaAtualizada(pessoa, pessoaEncontrada);
                    pessoaGateway.atualizar(id, buildPessoaAtualizada);
                    log.info("[Pessoa-Api][atualizarPessoa] Pessoa atualizada com sucesso! ID: {}", id);
                });
    }

    @Override
    public void excluirPessoa(final Long id) {
        Optional.ofNullable(id)
                .ifPresent(pessoaGateway::deletar);
        log.info("[Pessoa-Api][excluirPessoa] Pessoa excluida com sucesso! ID: {}", id);
    }

    private Pessoa buildPessoaAtualizada(final Pessoa novaPessoa, final Pessoa pessoaEncontrada) {
        return pessoaEncontrada.toBuilder()
                .nome(novaPessoa.getNome() != null ? novaPessoa.getNome() : pessoaEncontrada.getNome())
                .departamento(novaPessoa.getDepartamento() != null ? novaPessoa.getDepartamento() : pessoaEncontrada.getDepartamento())
                .build();
    }

}
