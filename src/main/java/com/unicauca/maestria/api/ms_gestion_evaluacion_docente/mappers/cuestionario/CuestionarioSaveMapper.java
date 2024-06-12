package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.cuestionario;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionario.Cuestionario;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.GenericMapper;
import org.mapstruct.InjectionStrategy; // Add this import statement
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public interface CuestionarioSaveMapper  extends GenericMapper<CuestionarioSaveDto, Cuestionario>{

    
} 
