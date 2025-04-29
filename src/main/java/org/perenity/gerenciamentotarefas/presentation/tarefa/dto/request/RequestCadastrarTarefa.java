package org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RequestCadastrarTarefa {
    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    private String descricao;

    @NotNull(message = "O prazo é obrigatório.")
    @Future(message = "O prazo deve ser uma data futura.")
    private LocalDateTime prazo;

    @NotBlank(message = "O departamento é obrigatório.")
    private String departamento;

}
