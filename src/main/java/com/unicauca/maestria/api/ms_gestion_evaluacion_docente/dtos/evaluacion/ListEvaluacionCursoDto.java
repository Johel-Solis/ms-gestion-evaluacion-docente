package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListEvaluacionCursoDto {

  private Long idCuestionario;
  private List<EvaluacionCursoDto> evaluacionesCurso;
  
}
