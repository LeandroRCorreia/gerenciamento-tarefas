package org.perenity.gerenciamentotarefas.business.pessoa.service;

import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasNomePeriodo;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasTotalHoraTarefa;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PessoaService {
    Collection<ResponseListarPessoasNomePeriodo> listarPessoasPorNomePeriodo(
            final String nome,
            final LocalDateTime inicio,
            final LocalDateTime fim);
    Collection<ResponseListarPessoasTotalHoraTarefa> listarPessoasComTotalHorasGastas();
    Pessoa cadastrarPessoa(final Pessoa pessoa);
    void atualizarPessoa(final Long id, final Pessoa pessoa);
    void excluirPessoa(final Long id);
}
