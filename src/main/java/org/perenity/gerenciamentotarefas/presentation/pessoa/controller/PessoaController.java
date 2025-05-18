package org.perenity.gerenciamentotarefas.presentation.pessoa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.perenity.gerenciamentotarefas.business.pessoa.service.PessoaService;
import org.perenity.gerenciamentotarefas.infra.ResponseDto;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request.RequestAtualizarPessoa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.request.RequestCadastrarPessoa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasNomePeriodo;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasTotalHoraTarefa;
import org.perenity.gerenciamentotarefas.presentation.pessoa.mapper.PessoaDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@RequiredArgsConstructor
@Slf4j
public class PessoaController {

    private final PessoaService pessoaService;
    private final PessoaDtoMapper pessoaDtoMapper;

    @GetMapping("/gastos")
    public ResponseEntity<ResponseDto<Collection<ResponseListarPessoasNomePeriodo>>> listarPessoasPorNomePeriodo(
            @RequestParam String nome,
            @RequestParam String dataInicio,
            @RequestParam String dataFim) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(dataInicio + "T00:00:00", formatter);
        LocalDateTime fim = LocalDateTime.parse(dataFim + "T23:59:59", formatter);

        Collection<ResponseListarPessoasNomePeriodo> lista = pessoaService.listarPessoasPorNomePeriodo(nome, inicio, fim);
        return ResponseEntity.ok(ResponseDto.sucess(lista));
    }

    @GetMapping()
    public ResponseEntity<ResponseDto<Collection<ResponseListarPessoasTotalHoraTarefa>>> listarPessoas() {
        Collection<ResponseListarPessoasTotalHoraTarefa> lista = pessoaService.listarPessoasComTotalHorasGastas();
        return ResponseEntity.ok(ResponseDto.sucess(lista));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<String>> cadastrarPessoa(@RequestBody @Valid final RequestCadastrarPessoa requestCadastrarPessoa) {
        return Optional.of(requestCadastrarPessoa)
                .map(pessoaDtoMapper::toModel)
                .map(pessoaService::cadastrarPessoa)
                .map(pessoaCadastrada -> {
                    String mensagem = "Pessoa criada com sucesso! ID: " + pessoaCadastrada.getId();
                    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.sucess(mensagem));
                })
                .orElseThrow();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> atualizarPessoa(
            @PathVariable final Long id,
            @RequestBody final RequestAtualizarPessoa requestAtualizarPessoa) {
        pessoaService.atualizarPessoa(id, pessoaDtoMapper.toModel(requestAtualizarPessoa));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.sucess());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deletarPessoa(@PathVariable final Long id){
        pessoaService.excluirPessoa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.sucess());
    }
}