package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import java.util.ArrayList;
import java.util.List;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos.AreaFormacionResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EvaluacionDetailDto {

    private List<AreaFormacionResponseDto> areasFormacion;
    private int cantidadEstudiantesResgistrados;

    public EvaluacionDetailDto() {
        areasFormacion = new ArrayList<>();
    }

}
