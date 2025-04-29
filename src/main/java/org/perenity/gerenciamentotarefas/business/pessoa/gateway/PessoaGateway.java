package org.perenity.gerenciamentotarefas.business.pessoa.gateway;

import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface PessoaGateway {
    Optional<Pessoa> buscarPessoa(final Long id);
    Collection<Pessoa> listarTodas();
    Collection<Pessoa> listarPessoasPorNomePeriodo(
            final String nome,
            final LocalDateTime dataInicio,
            final LocalDateTime dataFim);
    Pessoa cadastrar(final Pessoa pessoa);
    void atualizar(final Long id, final Pessoa pessoa);
    void deletar(final Long id);
}
