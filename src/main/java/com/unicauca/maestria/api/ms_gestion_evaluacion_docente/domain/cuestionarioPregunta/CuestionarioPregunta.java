package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionarioPregunta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "preguntas_cuestionarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_cuestionario", "id_pregunta" })
})
public class CuestionarioPregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cuestionario")
    private Cuestionario cuestionario;

    @ManyToOne
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

}
