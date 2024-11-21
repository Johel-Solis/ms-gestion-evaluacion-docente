package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos;

import java.util.ArrayList;
import java.util.List;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.docente.DocenteResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CursoResponseDto {

    private Long id;
    private String grupoCurso;
    private Long idArea;
    private String area;
    private Long codigo;
    private String asignatura;
    private List<DocenteResponseDto> docentes;
    private int cantidadEstudiantes;

    public CursoResponseDto() {
        docentes = new ArrayList<>();
    }

}
