package org.perenity.gerenciamentotarefas.presentation.tarefa.mapper;


import org.apache.coyote.Response;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestAlocarPessoaTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestCadastrarTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestFinalizarTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.response.ResponseListarTarefasPendentes;
import org.springframework.stereotype.Component;


@Component
public class TarefaDtoMapper {


    public Tarefa toModel(final RequestCadastrarTarefa requestCadastrarTarefa) {
        return Tarefa.builder()
                .titulo(requestCadastrarTarefa.getTitulo())
                .descricao(requestCadastrarTarefa.getDescricao())
                .prazo(requestCadastrarTarefa.getPrazo())
                .departamento(requestCadastrarTarefa.getDepartamento())
                .finalizado(Boolean.FALSE)
                .build();
    }

    public Tarefa toModel(final RequestAlocarPessoaTarefa requestAlocarPessoaTarefa) {
        return Tarefa.builder()
                .pessoaId(requestAlocarPessoaTarefa.getPessoaId())
                .build();
    }

    public Tarefa toModel(final RequestFinalizarTarefa requestFinalizarTarefa){
        return Tarefa.builder()
                .pessoaId(requestFinalizarTarefa.getPessoaId())
                .duracaoHoras(requestFinalizarTarefa.getDuracaoHoras())
                .build();
    }

    public ResponseListarTarefasPendentes toResponse(final Tarefa tarefa) {
        return ResponseListarTarefasPendentes.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .prazo(tarefa.getPrazo())
                .departamento(tarefa.getDepartamento())
                .finalizado(tarefa.getFinalizado())
                .build();
    }


}
