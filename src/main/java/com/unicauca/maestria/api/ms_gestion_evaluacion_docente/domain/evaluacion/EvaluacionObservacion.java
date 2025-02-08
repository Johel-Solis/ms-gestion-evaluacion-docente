package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion;

import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Estudiante;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "evaluacion_observacion")
public class EvaluacionObservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_evaluacion_curso_docente")
    private EvaluacionCursoDocente evaluacionCursoDocente;  

    private String observacion;

    @CreatedDate
    private LocalDateTime fecha_creacion; 

    public EvaluacionObservacion() {
        fecha_creacion = LocalDateTime.now();
    }
    
}
