package org.perenity.gerenciamentotarefas.integration.tarefa.mapper;

import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.integration.tarefa.model.TarefaEntity;
import org.springframework.stereotype.Component;

@Component
public class TarefaEntityMapper {

    public Tarefa toDomain(final TarefaEntity tarefaEntity){
        return Tarefa.builder()
                .id(tarefaEntity.getId())
                .titulo(tarefaEntity.getTitulo())
                .descricao(tarefaEntity.getDescricao())
                .prazo(tarefaEntity.getPrazo())
                .departamento(tarefaEntity.getDepartamento())
                .duracaoHoras(tarefaEntity.getDuracaoHoras())
                .finalizado(tarefaEntity.getFinalizado())
                .pessoaId(tarefaEntity.getPessoaId())
                .build();
    }

    public TarefaEntity toEntity(final Tarefa tarefa){
        return TarefaEntity.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .prazo(tarefa.getPrazo())
                .departamento(tarefa.getDepartamento())
                .duracaoHoras(tarefa.getDuracaoHoras())
                .finalizado(tarefa.getFinalizado())
                .pessoaId(tarefa.getPessoaId())
                .build();
    }

}
