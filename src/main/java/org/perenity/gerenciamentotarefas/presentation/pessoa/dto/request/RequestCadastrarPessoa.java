package org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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
public class RequestCadastrarPessoa {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotBlank(message = "O departamento é obrigatório")
    private String departamento;

}
