package org.perenity.gerenciamentotarefas.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.perenity.gerenciamentotarefas.infra.DefaultExceptionResponse;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto<T> {
    private T data;
    private Boolean sucess;
    private Collection<DefaultExceptionResponse> errors;

    public static ResponseDto<?> sucess() {
        return ResponseDto.builder()
                .sucess(true)
                .build();
    }

    public static <T> ResponseDto<T> sucess(T data) {
        return ResponseDto.<T>builder()
                .data(data)
                .sucess(true)
                .build();
    }

    public static <T> ResponseDto<?> error(Collection<DefaultExceptionResponse> errors) {
        return ResponseDto.builder()
                .sucess(false)
                .errors(errors)
                .build();
    }

    public static <T> ResponseDto<?> error(DefaultExceptionResponse error) {
        return ResponseDto.builder()
                .sucess(false)
                .errors(List.of(error))
                .build();
    }

}