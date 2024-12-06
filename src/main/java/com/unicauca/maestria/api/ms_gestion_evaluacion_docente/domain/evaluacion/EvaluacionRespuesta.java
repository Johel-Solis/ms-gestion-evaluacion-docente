package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Estudiante;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionarioPregunta.Pregunta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
// @NoArgsConstructor
@Builder
@Entity
@Table(name = "evaluacion_respuesta_estudiante")
public class EvaluacionRespuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_evaluacion_curso_docente")
    private EvaluacionCursoDocente evaluacionCursoDocente;

    @ManyToOne
    @JoinColumn(name = "id_pregunta")
    private Pregunta pregunta;

    private int valorRespuesta;

    @CreatedDate
    private LocalDateTime fechaCreacion;

    public EvaluacionRespuesta() {
        fechaCreacion = LocalDateTime.now();
    }
}
