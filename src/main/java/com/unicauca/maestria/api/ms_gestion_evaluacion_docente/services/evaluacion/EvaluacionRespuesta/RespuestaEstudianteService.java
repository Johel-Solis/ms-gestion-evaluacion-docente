package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionRespuesta;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionRespuetaSaveDto;

public interface RespuestaEstudianteService {
    public boolean saveRespuestaEstudiante(EvaluacionRespuetaSaveDto evaluacionRespuetaSaveDto);
    
}