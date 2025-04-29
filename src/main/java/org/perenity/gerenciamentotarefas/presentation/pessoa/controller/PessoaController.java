package org.perenity.gerenciamentotarefas.presentation.pessoa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.pessoa.service.PessoaService;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request.RequestAtualizarPessoa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request.RequestCadastrarPessoa;
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
    public ResponseEntity<String> cadastrarPessoa(@RequestBody @Valid final RequestCadastrarPessoa requestCadastrarPessoa) {
        return Optional.of(requestCadastrarPessoa)
                .map(pessoaDtoMapper::toModel)
                .map(pessoaService::cadastrarPessoa)
                .map(pessoa -> ResponseEntity.status(HttpStatus.CREATED)
                        .body("Pessoa criada com sucesso! ID: " + pessoa.getId()))
                .orElseThrow();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPessoa(
            @PathVariable final Long id,
            @RequestBody final RequestAtualizarPessoa requestAtualizarPessoa) {
        return Optional.of(requestAtualizarPessoa)
                .map(pessoaDtoMapper::toModel)
                .map(pessoaAtualizada -> {
                    pessoaService.atualizarPessoa(id, pessoaAtualizada);
                    return ResponseEntity.status(204).build();
                })
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPessoa(@PathVariable final Long id){
        return Optional.ofNullable(id)
                .map(pessoaId -> {
                    pessoaService.excluirPessoa(id);
                    return id;
                })
                .map(pessoaId -> ResponseEntity.status(HttpStatus.NO_CONTENT).build())
                .orElseThrow();
    }

}
