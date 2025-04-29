package org.perenity.gerenciamentotarefas.integration.pessoa.repository;

import org.perenity.gerenciamentotarefas.integration.pessoa.model.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Long> {

    @Query("""
        SELECT DISTINCT p
        FROM PessoaEntity p
        JOIN TarefaEntity t ON p.id = t.pessoaId
        WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
          AND t.prazo BETWEEN :inicio AND :fim
    """)
    Collection<PessoaEntity> listarPessoasPorNomeETarefasNoPeriodo(
            @Param("nome") String nome,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

}