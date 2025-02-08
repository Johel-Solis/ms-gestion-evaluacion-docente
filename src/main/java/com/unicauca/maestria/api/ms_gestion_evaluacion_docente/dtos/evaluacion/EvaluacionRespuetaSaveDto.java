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
public class EvaluacionRespuetaSaveDto {

    private Long idEvaluacionCursoDocente;
    private Long idEstudiante;
    private List<RespuestaEstudianteDto> respuestas;
    private String observacion;

}
