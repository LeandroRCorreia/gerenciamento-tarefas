package org.perenity.gerenciamentotarefas.presentation.tarefa.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.perenity.gerenciamentotarefas.business.tarefa.service.TarefaService;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestAlocarPessoaTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestCadastrarTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestFinalizarTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.mapper.TarefaDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @PutMapping("/alocar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> alocarPessoaTarefa(
            @PathVariable final Long id,
            @RequestBody @Valid RequestAlocarPessoaTarefa requestAlocarPessoaTarefa){
        return Optional.ofNullable(requestAlocarPessoaTarefa)
                .map(tarefaDtoMapper::toModel)
                .map(tarefa -> {
                    tarefaService.alocarTarefaPessoa(id, tarefa);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElseThrow();
    }

    @PutMapping("/finalizar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> finalizarTarefa(
            @PathVariable final Long id,
            @RequestBody @Valid RequestFinalizarTarefa requestFinalizarTarefa){
        return Optional.ofNullable(requestFinalizarTarefa)
                .map(tarefaDtoMapper::toModel)
                .map(tarefa -> {
                    tarefaService.finalizarTarefa(id, tarefa);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElseThrow();
    }

}
