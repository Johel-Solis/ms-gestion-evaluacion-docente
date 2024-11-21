package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.pregunta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Mappings;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionarioPregunta.Pregunta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.GenericMapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring")
public interface PreguntaResponseMapper extends GenericMapper<PreguntaResponseDto, Pregunta> {

    @Mappings({
            @Mapping(target = "fecha_creacion", expression = "java(convertFechaCreacionToString(pregunta.getFecha_creacion()))")
    })
    PreguntaResponseDto toDto(Pregunta pregunta);

    default String convertFechaCreacionToString(LocalDateTime fechaCreacion) {
        if (fechaCreacion == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaCreacion.format(formatter);
    }
}
