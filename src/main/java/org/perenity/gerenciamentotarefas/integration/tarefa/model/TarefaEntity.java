package org.perenity.gerenciamentotarefas.integration.tarefa.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarefa")
public class TarefaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String descricao;
    private LocalDateTime prazo;
    private String departamento;
    private Integer duracaoHoras;
    private Boolean finalizado;

    private Integer pessoaId;
}
