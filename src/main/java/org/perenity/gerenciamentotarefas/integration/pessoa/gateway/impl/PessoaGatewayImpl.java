package org.perenity.gerenciamentotarefas.integration.pessoa.gateway.impl;


import lombok.RequiredArgsConstructor;
import org.perenity.gerenciamentotarefas.business.pessoa.gateway.PessoaGateway;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.integration.pessoa.mapper.PessoaEntityMapper;
import org.perenity.gerenciamentotarefas.integration.pessoa.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaGatewayImpl implements PessoaGateway {

    private final PessoaEntityMapper pessoaEntityMapper;
    private final PessoaRepository pessoaRepository;

    @Override
    public Pessoa cadastrarPessoa(final Pessoa pessoa) {
        return Optional.ofNullable(pessoa)
                .map(pessoaEntityMapper::toEntity)
                .map(pessoaRepository::save)
                .map(pessoaEntityMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Pessoa inválida para cadastro"));
    }




}
