package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluacionResponseDto {

    private Long id;
    private int anio;
    private int periodo;

    private String nombreCuestionario;
    private int cantidadAsignaturas;
    private String estado;

    private String fechaCreacion;
    private String fechaInicio;
    private String fechaFin;

}
