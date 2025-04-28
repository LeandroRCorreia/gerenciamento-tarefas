package org.perenity.gerenciamentotarefas.presentation.pessoa.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.pessoa.service.impl.PessoaService;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request.RequestPessoa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.mapper.PessoaDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
@Slf4j
public class PessoaController {

    private final PessoaService pessoaService;
    private final PessoaDtoMapper pessoaDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> cadastrarPessoa(@RequestBody @Valid final RequestPessoa requestPessoa) {
        return Optional.of(requestPessoa)
                .map(pessoaDtoMapper::toModel)
                .map(pessoaService::cadastrarPessoa)
                .map(pessoa -> ResponseEntity.status(HttpStatus.CREATED)
                        .body("Pessoa criada com sucesso! ID: " + pessoa.getId()))
                .orElseThrow();
    }

}
