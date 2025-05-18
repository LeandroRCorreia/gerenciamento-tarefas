package org.perenity.gerenciamentotarefas.business.exception;


import lombok.Getter;

@Getter
public class DomainNotFoundException extends BusinessException {

    private static final String message = "Domain %s:%s not found";
    private final String name;
    private final Object value;

    public DomainNotFoundException(final String name, final Object value) {
        super(message.formatted(name, value));
        this.name = name;
        this.value = value;
    }
}
