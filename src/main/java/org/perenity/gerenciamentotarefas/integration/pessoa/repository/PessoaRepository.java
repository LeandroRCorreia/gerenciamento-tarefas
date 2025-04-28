package org.perenity.gerenciamentotarefas.integration.pessoa.repository;

import org.perenity.gerenciamentotarefas.integration.pessoa.model.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Integer> {
}