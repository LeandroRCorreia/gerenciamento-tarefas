package org.perenity.gerenciamentotarefas.business.tarefa.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.perenity.gerenciamentotarefas.business.pessoa.exception.PessoaNaoEncontradaException;
import org.perenity.gerenciamentotarefas.business.pessoa.gateway.PessoaGateway;
import org.perenity.gerenciamentotarefas.business.pessoa.model.Pessoa;
import org.perenity.gerenciamentotarefas.business.tarefa.exception.TarefaNaoEncontradaException;
import org.perenity.gerenciamentotarefas.business.tarefa.gateway.TarefaGateway;
import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaServiceImplTest {

    @Mock
    private TarefaGateway tarefaGateway;

    @Mock
    private PessoaGateway pessoaGateway;

    @InjectMocks
    private TarefaServiceImpl tarefaService;

    @Test
    @DisplayName("Deve alocar tarefa com sucesso quando pessoa e departamento forem válidos")
    void deveAlocarTarefaComSucessoQuandoPessoaEdepartamentoForemValidos() {
        Long tarefaId = 1L;
        Long pessoaId = 10L;

        Tarefa tarefa = Tarefa.builder().pessoaId(pessoaId).build();
        Tarefa tarefaEncontrada = Tarefa.builder().id(tarefaId).departamento("TI").build();
        Pessoa pessoaEncontrada = Pessoa.builder().id(pessoaId).departamento("TI").build();

        when(tarefaGateway.buscarTarefa(tarefaId)).thenReturn(Optional.of(tarefaEncontrada));
        when(pessoaGateway.buscarPessoa(pessoaId)).thenReturn(Optional.of(pessoaEncontrada));

        tarefaService.alocarTarefaPessoa(tarefaId, tarefa);

        ArgumentCaptor<Tarefa> captor = ArgumentCaptor.forClass(Tarefa.class);
        verify(tarefaGateway).atualizar(eq(tarefaId), captor.capture());

        assertEquals(pessoaId, captor.getValue().getPessoaId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a tarefa não for encontrada")
    void deveLancarExcecaoQuandoTarefaNaoForEncontrada() {
        Long tarefaId = 1L;
        Tarefa novaTarefa = Tarefa.builder().pessoaId(10L).build();

        when(tarefaGateway.buscarTarefa(tarefaId)).thenReturn(Optional.empty());

        assertThrows(TarefaNaoEncontradaException.class, () ->
                tarefaService.alocarTarefaPessoa(tarefaId, novaTarefa)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando a pessoa não for encontrada")
    void deveLancarExcecaoQuandoPessoaNaoForEncontrada() {
        Long tarefaId = 1L;
        Long pessoaId = 10L;

        Tarefa novaTarefa = Tarefa.builder().pessoaId(pessoaId).build();
        Tarefa tarefaExistente = Tarefa.builder().id(tarefaId).departamento("TI").build();

        when(tarefaGateway.buscarTarefa(tarefaId)).thenReturn(Optional.of(tarefaExistente));
        when(pessoaGateway.buscarPessoa(pessoaId)).thenReturn(Optional.empty());

        assertThrows(PessoaNaoEncontradaException.class, () ->
                tarefaService.alocarTarefaPessoa(tarefaId, novaTarefa)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando departamentos forem diferentes")
    void deveLancarExcecaoQuandoDepartamentosForemDiferentes() {
        Long tarefaId = 1L;
        Long pessoaId = 10L;

        Tarefa tarefa = Tarefa.builder().pessoaId(pessoaId).build();
        Tarefa tarefaEncontrada = Tarefa.builder().id(tarefaId).departamento("TI").build();
        Pessoa pessoaEncontrada = Pessoa.builder().id(pessoaId).departamento("RH").build();

        when(tarefaGateway.buscarTarefa(tarefaId)).thenReturn(Optional.of(tarefaEncontrada));
        when(pessoaGateway.buscarPessoa(pessoaId)).thenReturn(Optional.of(pessoaEncontrada));

        assertThrows(IllegalArgumentException.class,
                () -> tarefaService.alocarTarefaPessoa(tarefaId, tarefa));
    }

    @Test
    @DisplayName("Deve finalizar tarefa com sucesso")
    void deveFinalizarTarefaComSucesso() {
        Long tarefaId = 1L;
        Long pessoaId = 10L;
        Long duracaoHoras = 5L;

        Tarefa entrada = Tarefa.builder().pessoaId(pessoaId).duracaoHoras(duracaoHoras).build();
        Tarefa existente = Tarefa.builder().id(tarefaId).pessoaId(pessoaId).build();
        Pessoa pessoa = Pessoa.builder().id(pessoaId).build();

        when(tarefaGateway.buscarTarefa(tarefaId)).thenReturn(Optional.of(existente));
        when(pessoaGateway.buscarPessoa(pessoaId)).thenReturn(Optional.of(pessoa));

        tarefaService.finalizarTarefa(tarefaId, entrada);

        ArgumentCaptor<Tarefa> captor = ArgumentCaptor.forClass(Tarefa.class);
        verify(tarefaGateway).atualizar(eq(tarefaId), captor.capture());

        Tarefa resultado = captor.getValue();
        assertTrue(resultado.getFinalizado());
        assertEquals(duracaoHoras, resultado.getDuracaoHoras());
    }

}
