package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder@AllArgsConstructor@NoArgsConstructor
public class RespuestaEstudianteDto {

    private Long idPregunta;
    private Integer valor;
    
}
