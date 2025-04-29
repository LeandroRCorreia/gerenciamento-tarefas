package org.perenity.gerenciamentotarefas.presentation.tarefa.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.perenity.gerenciamentotarefas.business.tarefa.service.TarefaService;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestCadastrarTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.mapper.TarefaDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaDtoMapper tarefaDtoMapper;
    private final TarefaService tarefaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> cadastrarTarefa(
            @RequestBody @Valid final RequestCadastrarTarefa requestCadastrarTarefa){
        return Optional.of(requestCadastrarTarefa)
                .map(tarefaDtoMapper::toModel)
                .map(tarefaService::cadastrarTarefa)
                .map(tarefa -> ResponseEntity.status(HttpStatus.CREATED)
                        .body("Tarefa criada com sucesso! ID: " + tarefa.getId()))
                .orElseThrow();
    }

}
