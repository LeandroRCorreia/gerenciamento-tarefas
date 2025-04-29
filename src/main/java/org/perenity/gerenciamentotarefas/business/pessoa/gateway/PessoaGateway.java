package org.perenity.gerenciamentotarefas.business.pessoa.gateway;

import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;

import java.util.Optional;

public interface PessoaGateway {
    Optional<Pessoa> buscarPessoa(final Long id);
    Pessoa cadastrar(final Pessoa pessoa);
    void atualizar(final Long id, final Pessoa pessoa);
    void deletar(final Long id);
}
