package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.cuestionario;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.CamposUnicosDto;


@Service
public class InformacionUnicaCuestionario implements Function<CuestionarioSaveDto, CamposUnicosDto>{

    @Override
    public CamposUnicosDto apply(CuestionarioSaveDto cuestionario) {
        return CamposUnicosDto.builder()
                .nombre(cuestionario.getNombre())
                .build();
    }
    
}
