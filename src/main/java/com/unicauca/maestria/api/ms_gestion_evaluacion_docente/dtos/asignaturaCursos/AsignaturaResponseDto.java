package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AsignaturaResponseDto {

    private Long id;
    private String nombre;
    private String codigo;
    private String estado;
    private List<CursoResponseDto> cursos;

    public AsignaturaResponseDto() {
    }
}
