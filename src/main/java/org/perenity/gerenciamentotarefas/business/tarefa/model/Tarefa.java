package org.perenity.gerenciamentotarefas.business.tarefa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tarefa {
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime prazo;
    private String departamento;
    private Boolean finalizado;
    private Long duracaoHoras;
    private Long pessoaId;

}
