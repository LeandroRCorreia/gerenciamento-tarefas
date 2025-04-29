package org.perenity.gerenciamentotarefas.integration.tarefa.repository;


import org.perenity.gerenciamentotarefas.integration.tarefa.model.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<TarefaEntity, Long> {

}
