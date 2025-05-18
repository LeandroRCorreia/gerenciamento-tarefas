package org.perenity.gerenciamentotarefas.business.exception;


public class BusinessException extends RuntimeException {
    public BusinessException(final String message) {
        super(message);
    }
}
