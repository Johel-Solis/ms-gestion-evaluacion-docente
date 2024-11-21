package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AreaFormacionResponseDto {

    private Long idArea;
    private String nombre;
    private List<CursoResponseDto> cursos;

    public AreaFormacionResponseDto() {
        cursos = new ArrayList<>();
    }
}
