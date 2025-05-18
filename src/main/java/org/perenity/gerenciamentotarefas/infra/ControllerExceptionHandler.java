package org.perenity.gerenciamentotarefas.infra;

import org.perenity.gerenciamentotarefas.business.exception.DomainNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> domainNotFoundException(final DomainNotFoundException ex) {
        final DefaultExceptionResponse defaultExceptionResponse = DefaultExceptionResponse
                .builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.error(defaultExceptionResponse));
    }
}
