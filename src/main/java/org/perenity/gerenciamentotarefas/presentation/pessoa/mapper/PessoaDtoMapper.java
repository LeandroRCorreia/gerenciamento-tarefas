package org.perenity.gerenciamentotarefas.presentation.pessoa.mapper;


import lombok.AllArgsConstructor;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request.RequestPessoa;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PessoaDtoMapper {

    public Pessoa toModel(final RequestPessoa requestPessoa){
        return Pessoa.builder()
                .nome(requestPessoa.getNome())
                .departamento(requestPessoa.getDepartamento())
                .build();
    }

}
