package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor @Builder
public class PreguntaResponseDto {

    private Long id;
    private String nombre;
    private String observacion;
    private String estado;
    private String fecha_creacion;


    
}
