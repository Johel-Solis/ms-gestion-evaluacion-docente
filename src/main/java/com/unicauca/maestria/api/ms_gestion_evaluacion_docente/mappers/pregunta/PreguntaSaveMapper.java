package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.pregunta;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionarioPregunta.Pregunta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.GenericMapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring")
public interface PreguntaSaveMapper extends GenericMapper<PreguntaSaveDto, Pregunta> {

}