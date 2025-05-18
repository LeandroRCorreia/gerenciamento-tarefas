package org.perenity.gerenciamentotarefas.business.pessoa.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.exception.DomainNotFoundException;
import org.perenity.gerenciamentotarefas.business.pessoa.exception.PessoaNaoEncontradaException;
import org.perenity.gerenciamentotarefas.business.pessoa.gateway.PessoaGateway;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.business.pessoa.service.PessoaService;
import org.perenity.gerenciamentotarefas.business.tarefa.gateway.TarefaGateway;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasNomePeriodo;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasTotalHoraTarefa;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
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
        log.info("[Pessoa-Api][listarPessoasPorNomePeriodo] Iniciando consulta de pessoas com nome '{}' no período de {} até {}", nome, inicio, fim);

        final Collection<Pessoa> pessoas = pessoaGateway.listarPessoasPorNomePeriodo(nome, inicio, fim);

        log.info("[Pessoa-Api][listarPessoasPorNomePeriodo] Encontradas {} pessoas", pessoas.size());

        return pessoas.stream().map(pessoa -> {
            log.debug("[Pessoa-Api][listarPessoasPorNomePeriodo] Processando pessoa: {}", pessoa.getNome());

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
        log.info("[Pessoa-Api][listarPessoasComTotalHorasGastas] Iniciando consulta para listar pessoas com total de horas gastas.");

        final Collection<Pessoa> pessoas = pessoaGateway.listarTodas();

        log.info("[Pessoa-Api][listarPessoasComTotalHorasGastas] Encontradas {} pessoas", pessoas.size());

        return pessoas.stream().map(pessoa -> {
            log.debug("[Pessoa-Api][listarPessoasComTotalHorasGastas] Processando pessoa: {}", pessoa.getNome());

            final Collection<Tarefa> tarefas = tarefaGateway.listarTarefasPorPessoa(pessoa.getId());

            final Long totalHoras = pessoa.calcularTotalHorasGastas(tarefas);

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
        log.info("[Pessoa-Api][cadastrarPessoa] Iniciando o cadastro de uma nova pessoa.");

        return Optional.ofNullable(pessoa)
                .map(pessoaGateway::cadastrar)
                .map(pessoaCadastrada -> {
                    log.info("[Pessoa-Api][cadastrarPessoa] Pessoa cadastrada com sucesso! ID: {}", pessoaCadastrada.getId());
                    return pessoaCadastrada;
                })
                .orElseThrow(() -> {
                    log.error("[Pessoa-Api][cadastrarPessoa] Falha ao cadastrar a pessoa.");
                    return new IllegalArgumentException("Pessoa inválida");
                });
    }

    @Override
    public void atualizarPessoa(final Long id, final Pessoa pessoa) {
        log.info("[Pessoa-Api][atualizarPessoa] Iniciando a atualização da pessoa com ID: {}", id);

        Optional.ofNullable(pessoa)
                .ifPresent(p -> {
                    final Pessoa pessoaEncontrada = pessoaGateway
                            .buscarPessoa(id)
                            .orElseThrow(() -> new PessoaNaoEncontradaException(id));
                    final Pessoa buildPessoaAtualizada = buildPessoaAtualizada(pessoa, pessoaEncontrada);
                    pessoaGateway.atualizar(id, buildPessoaAtualizada);
                    log.info("[Pessoa-Api][atualizarPessoa] Pessoa atualizada com sucesso! ID: {}", id);
                });
    }

    @Override
    public void excluirPessoa(final Long id) {
        log.info("[Pessoa-Api][excluirPessoa] Iniciando a exclusão da pessoa com ID: {}", id);

        Optional.ofNullable(id)
                .ifPresent(pessoaGateway::deletar);
        log.info("[Pessoa-Api][excluirPessoa] Pessoa excluída com sucesso! ID: {}", id);
    }

    private Pessoa buildPessoaAtualizada(final Pessoa novaPessoa, final Pessoa pessoaEncontrada) {
        return pessoaEncontrada.toBuilder()
                .nome(novaPessoa.getNome() != null ? novaPessoa.getNome() : pessoaEncontrada.getNome())
                .departamento(novaPessoa.getDepartamento() != null ? novaPessoa.getDepartamento() : pessoaEncontrada.getDepartamento())
                .build();
    }
}
