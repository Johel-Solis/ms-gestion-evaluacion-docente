package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario;

import java.util.ArrayList;
import java.util.List;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor @Builder
public class CuestionarioResponseDto {

    private Long id;
    private String nombre;
    private String observacion;
    private Integer cantidad_preguntas;
    private String estado;
    private String fecha_creacion;
    private List<PreguntaResponseDto> preguntas;


    public CuestionarioResponseDto() {
        preguntas=new ArrayList<>();
    }

    
}
