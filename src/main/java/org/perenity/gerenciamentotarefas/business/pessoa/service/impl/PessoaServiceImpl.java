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
                .map(pessoaGateway::cadastrarPessoa)
                .map(pessoaCadastrada -> {
                    log.info("[Pessoa-Api][cadastrarPessoa] Pessoa cadastrada com sucesso! ID: {}", pessoaCadastrada.getId());
                    return pessoaCadastrada;
                })
                .orElseThrow(() -> new RuntimeException("Pessoa não pode ser nula"));
    }
}
