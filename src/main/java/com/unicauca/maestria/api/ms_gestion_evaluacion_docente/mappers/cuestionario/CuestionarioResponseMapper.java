package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.cuestionario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.InjectionStrategy;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionario.Cuestionario;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.GenericMapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public interface CuestionarioResponseMapper extends GenericMapper<CuestionarioResponseDto, Cuestionario>{

    @Override
    // @Mapping(target = "cantidad_preguntas", expression = "java(entity.getPreguntas().size())")
    
     @Mappings({
        @Mapping(target = "fecha_creacion", expression = "java(convertFechaCreacionToString(cuestionario.getFecha_creacion()))"),
        @Mapping(target = "preguntas", ignore = true)
    })
    CuestionarioResponseDto toDto(Cuestionario cuestionario);
    default String convertFechaCreacionToString(LocalDateTime fechaCreacion) {
        if (fechaCreacion == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fechaCreacion.format(formatter);
    }

    
}
