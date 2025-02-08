package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import java.util.List;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PromedioRespuestaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionEstadisticaDocenteDto {

    private int totalEvaluaciones;
    private int totalRespondidas;
    private List<PromedioRespuestaDTO> promedioRespuestas;
    private List<String> observaciones;
}
