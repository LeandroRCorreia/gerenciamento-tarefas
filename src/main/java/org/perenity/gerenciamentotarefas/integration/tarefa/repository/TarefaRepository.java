package org.perenity.gerenciamentotarefas.integration.tarefa.repository;


import org.perenity.gerenciamentotarefas.business.tarefa.model.Tarefa;
import org.perenity.gerenciamentotarefas.integration.tarefa.model.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface TarefaRepository extends JpaRepository<TarefaEntity, Long> {
    Collection<TarefaEntity> findByPessoaIdAndPrazoBetween(
            Long pessoaId,
            LocalDateTime inicio,
            LocalDateTime fim);

    Collection<TarefaEntity> findTop3ByPessoaIdIsNullOrderByPrazoAsc();

    Collection<TarefaEntity> findByPessoaId(Long pessoaId);

}
