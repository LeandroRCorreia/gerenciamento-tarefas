package org.perenity.gerenciamentotarefas.business.pessoa.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pessoa {
    @With
    private Long id;
    private String nome;
    private String departamento;

    public Long calcularTotalHorasGastas(Collection<Tarefa> tarefas) {
        if (tarefas == null || tarefas.isEmpty()) {
            return 0L;
        }

        return tarefas.stream()
                .filter(t -> Boolean.TRUE.equals(t.getFinalizado()) && t.getDuracaoHoras() != null)
                .mapToLong(Tarefa::getDuracaoHoras)
                .sum();
    }

    public Double calcularMediaHorasGastas(Collection<Tarefa> tarefas) {
        if (tarefas == null || tarefas.isEmpty()) {
            return 0.0;
        }

        return tarefas.stream()
                .filter(t -> Boolean.TRUE.equals(t.getFinalizado()) && t.getDuracaoHoras() != null)
                .mapToLong(Tarefa::getDuracaoHoras)
                .average()
                .orElse(0.0);
    }

}
