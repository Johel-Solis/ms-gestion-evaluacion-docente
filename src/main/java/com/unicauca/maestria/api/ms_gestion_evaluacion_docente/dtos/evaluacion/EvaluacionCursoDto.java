package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluacionCursoDto {

  private String asignatura;
  private String docente;
  private String estado;
  private float nota;
  private Long idEvaluacionCurso;
  
}
