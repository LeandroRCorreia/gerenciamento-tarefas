package org.perenity.gerenciamentotarefas.business.tarefa.exception;

import lombok.Getter;
import org.perenity.gerenciamentotarefas.business.exception.DomainNotFoundException;

@Getter
public class TarefaNaoEncontradaException extends DomainNotFoundException {
    private static final String DOMAIN_NAME = "Tarefa";
    private final Object value;


    public TarefaNaoEncontradaException(Object value) {
        super(DOMAIN_NAME, value);
        this.value = value;
    }
}
