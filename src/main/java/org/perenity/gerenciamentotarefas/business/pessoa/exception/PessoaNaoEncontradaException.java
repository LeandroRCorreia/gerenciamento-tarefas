package org.perenity.gerenciamentotarefas.business.pessoa.exception;

import lombok.Getter;
import org.perenity.gerenciamentotarefas.business.exception.DomainNotFoundException;


@Getter
public class PessoaNaoEncontradaException extends DomainNotFoundException {
    private static final String DOMAIN_NAME = "Pessoa";
    private final Object value;


    public PessoaNaoEncontradaException(Object idValue) {
        super(DOMAIN_NAME, idValue);
        this.value = idValue;
    }
}
