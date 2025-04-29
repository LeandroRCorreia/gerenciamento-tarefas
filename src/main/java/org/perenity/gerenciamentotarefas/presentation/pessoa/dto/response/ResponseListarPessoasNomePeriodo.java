package org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseListarPessoasNomePeriodo {
    private Long pessoaId;
    private String nome;
    private String departamento;
    private Double mediaHorasGastaPorTarefa;
}
