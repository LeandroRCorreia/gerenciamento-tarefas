package org.perenity.gerenciamentotarefas.business.pessoa.service;

import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;

public interface PessoaService {

    Pessoa cadastrarPessoa(final Pessoa pessoa);
    void atualizarPessoa(final Long id, final Pessoa pessoa);
    void excluirPessoa(final Long id);
}
