package org.perenity.gerenciamentotarefas.presentation.tarefa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.perenity.gerenciamentotarefas.business.tarefa.service.TarefaService;
import org.perenity.gerenciamentotarefas.infra.ResponseDto;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestAlocarPessoaTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestCadastrarTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.request.RequestFinalizarTarefa;
import org.perenity.gerenciamentotarefas.presentation.tarefa.dto.response.ResponseListarTarefasPendentes;
import org.perenity.gerenciamentotarefas.presentation.tarefa.mapper.TarefaDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaDtoMapper tarefaDtoMapper;
    private final TarefaService tarefaService;

    @GetMapping("/pendentes")
    public ResponseEntity<ResponseDto<Collection<ResponseListarTarefasPendentes>>> listarTarefasPendentes() {
        Collection<ResponseListarTarefasPendentes> lista = tarefaService.listarTarefasPendentes()
                .stream()
                .map(tarefaDtoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(ResponseDto.sucess(lista));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> cadastrarTarefa(
            @RequestBody @Valid final RequestCadastrarTarefa requestCadastrarTarefa) {
        return Optional.of(requestCadastrarTarefa)
                .map(tarefaDtoMapper::toModel)
                .map(tarefaService::cadastrarTarefa)
                .map(tarefa -> {
                    String mensagem = "Tarefa criada com sucesso! ID: " + tarefa.getId();
                    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.sucess(mensagem));
                })
                .orElseThrow(); // nunca deve cair aqui pois o corpo é validado
    }

    @PutMapping("/alocar/{id}")
    public ResponseEntity<ResponseDto<?>> alocarPessoaTarefa(
            @PathVariable final Long id,
            @RequestBody @Valid final RequestAlocarPessoaTarefa requestAlocarPessoaTarefa) {

        tarefaService.alocarTarefaPessoa(id, tarefaDtoMapper.toModel(requestAlocarPessoaTarefa));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.sucess());
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<ResponseDto<?>> finalizarTarefa(
            @PathVariable final Long id,
            @RequestBody @Valid final RequestFinalizarTarefa requestFinalizarTarefa) {

        tarefaService.finalizarTarefa(id, tarefaDtoMapper.toModel(requestFinalizarTarefa));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.sucess());
    }
}
