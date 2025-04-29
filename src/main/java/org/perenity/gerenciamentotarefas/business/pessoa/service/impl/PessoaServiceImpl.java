package org.perenity.gerenciamentotarefas.business.pessoa.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.pessoa.gateway.PessoaGateway;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PessoaServiceImpl implements PessoaService {

    private final PessoaGateway pessoaGateway;

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
                    Optional<Pessoa> optionalPessoa = pessoaGateway.buscarPessoa(id);
                    // TODO: fazer com que essa exception dispare 404 e seja tratada corretamente
                    if (optionalPessoa.isEmpty()) throw new RuntimeException("Pessoa não encontrada");
                    pessoaGateway.atualizar(id, pessoa);
                    log.info("[Pessoa-Api][atualizarPessoa] Pessoa atualizada com sucesso! ID: {}", id);
                });
    }

    @Override
    public void excluirPessoa(final Long id) {
        Optional.ofNullable(id)
                .ifPresent(pessoaGateway::deletar);
        log.info("[Pessoa-Api][excluirPessoa] Pessoa excluida com sucesso! ID: {}", id);
    }

}
