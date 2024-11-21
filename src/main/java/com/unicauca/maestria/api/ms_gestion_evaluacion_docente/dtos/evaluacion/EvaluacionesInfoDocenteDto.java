package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionesInfoDocenteDto {

    private Long idEvaluacionCursoDocente;
    private String nombreEstudiante;
    private String estado;
    private float notafinal;
    private String asignatura;

}
