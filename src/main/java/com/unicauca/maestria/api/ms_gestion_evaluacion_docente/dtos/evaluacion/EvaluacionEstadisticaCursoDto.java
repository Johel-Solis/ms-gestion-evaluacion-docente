package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionEstadisticaCursoDto {

    private String asignatura;
    private String docente;
    private int totalEvaluaciones;
    private int totalRespondidas;
    private float notaPromedio;
    private List<String> observaciones;

}
