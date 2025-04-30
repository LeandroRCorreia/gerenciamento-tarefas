package org.perenity.gerenciamentotarefas.business.pessoa.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.perenity.gerenciamentotarefas.business.pessoa.gateway.PessoaGateway;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.business.tarefa.gateway.TarefaGateway;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.exception.NotFoundException;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasNomePeriodo;
import org.perenity.gerenciamentotarefas.presentation.pessoa.dto.response.ResponseListarPessoasTotalHoraTarefa;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceImplTest {

    @Mock
    private PessoaGateway pessoaGateway;

    @Mock
    private TarefaGateway tarefaGateway;

    @InjectMocks
    private PessoaServiceImpl pessoaServiceImpl;

    @Test
    @DisplayName("Deve calcular corretamente a média de horas gastas por tarefa finalizada no período informado")
    void deveCalcularMediaHorasPorTarefaFinalizadaNoPeriodo() {
        Pessoa pessoa = Pessoa.builder().id(1L).nome("João").departamento("TI").build();
        Tarefa tarefa1 = Tarefa.builder().duracaoHoras(3L).finalizado(true).pessoaId(1L).build();
        Tarefa tarefa2 = Tarefa.builder().duracaoHoras(5L).finalizado(true).pessoaId(1L).build();

        LocalDateTime inicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fim = LocalDateTime.now();

        when(pessoaGateway.listarPessoasPorNomePeriodo("João", inicio, fim))
                .thenReturn(List.of(pessoa));
        when(tarefaGateway.listarTarefasPorPessoaEPeriodo(1L, inicio, fim))
                .thenReturn(List.of(tarefa1, tarefa2));

        Collection<ResponseListarPessoasNomePeriodo> resultado =
                pessoaServiceImpl.listarPessoasPorNomePeriodo("João", inicio, fim);

        assertEquals(1, resultado.size());
        ResponseListarPessoasNomePeriodo resposta = resultado.iterator().next();
        assertEquals("João", resposta.getNome());
        assertEquals("TI", resposta.getDepartamento());
        assertEquals(4.0, resposta.getMediaHorasGastaPorTarefa());
    }

    @Test
    @DisplayName("Deve retornar média zero quando a lista de tarefas estiver nula ou vazia")
    void deveRetornarZeroQuandoListaTarefasNulaOuVazia() {
        Pessoa pessoa = Pessoa.builder().build();

        assertEquals(0.0, pessoa.calcularMediaHorasGastas(null));
        assertEquals(0.0, pessoa.calcularMediaHorasGastas(List.of()));
    }

    @Test
    @DisplayName("Deve listar pessoas com o total de horas gastas apenas em tarefas finalizadas")
    void deveListarPessoasComTotalDeHorasGastasEmTarefasFinalizadas() {
        Pessoa pessoa1 = Pessoa.builder().id(1L).nome("Alice").departamento("TI").build();
        Pessoa pessoa2 = Pessoa.builder().id(2L).nome("Bob").departamento("RH").build();

        Tarefa tarefa1 = Tarefa.builder().pessoaId(1L).duracaoHoras(3L).finalizado(true).build();
        Tarefa tarefa2 = Tarefa.builder().pessoaId(1L).duracaoHoras(2L).finalizado(true).build();
        Tarefa tarefaNaoFinalizada = Tarefa.builder().pessoaId(1L).duracaoHoras(5L).finalizado(false).build();
        Tarefa tarefaSemDuracao = Tarefa.builder().pessoaId(1L).duracaoHoras(null).finalizado(true).build();

        when(pessoaGateway.listarTodas()).thenReturn(List.of(pessoa1, pessoa2));
        when(tarefaGateway.listarTarefasPorPessoa(1L)).thenReturn(List.of(tarefa1, tarefa2, tarefaNaoFinalizada, tarefaSemDuracao));
        when(tarefaGateway.listarTarefasPorPessoa(2L)).thenReturn(List.of());

        Collection<ResponseListarPessoasTotalHoraTarefa> resultado =
                pessoaServiceImpl.listarPessoasComTotalHorasGastas();

        assertEquals(2, resultado.size());

        ResponseListarPessoasTotalHoraTarefa pessoaResultado1 = resultado.stream()
                .filter(p -> p.getPessoaId().equals(1L)).findFirst().orElseThrow();
        assertEquals("Alice", pessoaResultado1.getNome());
        assertEquals("TI", pessoaResultado1.getDepartamento());
        assertEquals(5L, pessoaResultado1.getTotalHorasGastas());

        ResponseListarPessoasTotalHoraTarefa pessoaResultado2 = resultado.stream()
                .filter(p -> p.getPessoaId().equals(2L)).findFirst().orElseThrow();
        assertEquals("Bob", pessoaResultado2.getNome());
        assertEquals("RH", pessoaResultado2.getDepartamento());
        assertEquals(0L, pessoaResultado2.getTotalHorasGastas());
    }

    @Test
    @DisplayName("Deve cadastrar uma pessoa com sucesso")
    void deveCadastrarPessoaComSucesso() {
        Pessoa pessoa = Pessoa.builder().id(1L).nome("João").departamento("TI").build();

        when(pessoaGateway.cadastrar(pessoa)).thenReturn(pessoa);

        Pessoa pessoaCadastrada = pessoaServiceImpl.cadastrarPessoa(pessoa);

        assertEquals(1L, pessoaCadastrada.getId());
        assertEquals("João", pessoaCadastrada.getNome());
        assertEquals("TI", pessoaCadastrada.getDepartamento());
        verify(pessoaGateway, times(1)).cadastrar(pessoa);
    }

    @Test
    @DisplayName("Deve lançar exceção quando pessoa não for encontrada na atualização")
    void deveLancarExcecaoQuandoPessoaNaoForEncontradaNaAtualizacao() {
        Long idInvalido = 999L;
        Pessoa pessoaAtualizada = Pessoa.builder().nome("Novo Nome").departamento("Novo Departamento").build();

        when(pessoaGateway.buscarPessoa(idInvalido)).thenReturn(Optional.empty());

        try {
            pessoaServiceImpl.atualizarPessoa(idInvalido, pessoaAtualizada);
        } catch (Exception e) {
            assertInstanceOf(NotFoundException.class, e);
        }

        verify(pessoaGateway, times(1)).buscarPessoa(idInvalido);
    }

}
