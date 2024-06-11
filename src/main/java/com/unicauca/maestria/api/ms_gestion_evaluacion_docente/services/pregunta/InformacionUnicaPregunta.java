package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.pregunta;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.CamposUnicosDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaSaveDto;

@Service
public class InformacionUnicaPregunta implements Function<PreguntaSaveDto, CamposUnicosDto>{

    @Override
    public CamposUnicosDto apply(PreguntaSaveDto pregunta) {
        return CamposUnicosDto.builder()
                .nombre(pregunta.getNombre())
                .build();
    }
    
}
