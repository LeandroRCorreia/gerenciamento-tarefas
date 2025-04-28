package org.perenity.gerenciamentotarefas.integration.pessoa.mapper;


import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.integration.pessoa.model.PessoaEntity;
import org.springframework.stereotype.Component;

@Component
public class PessoaEntityMapper {

    public Pessoa toDomain(final PessoaEntity pessoaEntity) {
        return Pessoa.builder()
                .id(pessoaEntity.getId())
                .nome(pessoaEntity.getNome())
                .departamento(pessoaEntity.getDepartamento())
                .build();
    }

    public PessoaEntity toEntity(final Pessoa pessoa) {
        return PessoaEntity.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .departamento(pessoa.getDepartamento())
                .build();
    }

}
