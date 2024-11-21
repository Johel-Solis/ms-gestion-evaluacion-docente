package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.evaluacion;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.GenericMapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring")
public interface EvaluacionSaveMapper extends GenericMapper<EvaluacionSaveDto, EvaluacionDocente> {

}
