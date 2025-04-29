package org.perenity.gerenciamentotarefas.integration.pessoa.gateway.impl;


import lombok.RequiredArgsConstructor;
import org.perenity.gerenciamentotarefas.business.pessoa.gateway.PessoaGateway;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.integration.pessoa.mapper.PessoaEntityMapper;
import org.perenity.gerenciamentotarefas.integration.pessoa.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaGatewayImpl implements PessoaGateway {

    private final PessoaEntityMapper pessoaEntityMapper;
    private final PessoaRepository pessoaRepository;

    @Override
    public Optional<Pessoa> buscarPessoa(final Long id) {
        return Optional.ofNullable(id)
                .flatMap(pessoaRepository::findById)
                .map(pessoaEntityMapper::toDomain);
    }

    @Override
    public Collection<Pessoa> listarTodas() {
        return pessoaRepository.findAll()
                .stream()
                .map(pessoaEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Collection<Pessoa> listarPessoasPorNomePeriodo(
            final String nome,
            final LocalDateTime dataInicio,
            final LocalDateTime dataFim) {
        return pessoaRepository
                .listarPessoasPorNomeETarefasNoPeriodo(nome, dataInicio, dataFim)
                .stream()
                .map(pessoaEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Pessoa cadastrar(final Pessoa pessoa) {
        return Optional.ofNullable(pessoa)
                .map(pessoaEntityMapper::toEntity)
                .map(pessoaRepository::save)
                .map(pessoaEntityMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Pessoa inválida para cadastro"));
    }

    @Override
    public void atualizar(final Long id, final Pessoa pessoa) {
        Optional.ofNullable(id)
                .map(pessoa::withId)
                .map(pessoaEntityMapper::toEntity)
                .map(pessoaRepository::save)
                .map(pessoaEntityMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Pessoa inválida para atualização"));
    }

    @Override
    public void deletar(final Long id) {
        Optional.ofNullable(id)
                .ifPresent(pessoaRepository::deleteById);
    }

}
