package org.perenity.gerenciamentotarefas.presentation.pessoa.mapper;


import lombok.AllArgsConstructor;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request.RequestAtualizarPessoa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request.RequestCadastrarPessoa;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PessoaDtoMapper {

    public Pessoa toModel(final RequestCadastrarPessoa requestCadastrarPessoa){
        return Pessoa.builder()
                .nome(requestCadastrarPessoa.getNome())
                .departamento(requestCadastrarPessoa.getDepartamento())
                .build();
    }

    public Pessoa toModel(final RequestAtualizarPessoa requestCadastrarPessoa){
        return Pessoa.builder()
                .nome(requestCadastrarPessoa.getNome())
                .departamento(requestCadastrarPessoa.getDepartamento())
                .build();
    }

}
