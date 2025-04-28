package org.perenity.gerenciamentotarefas.business.pessoa.gateway;

import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;

public interface PessoaGateway {
    Pessoa cadastrarPessoa(final Pessoa pessoa);
}
