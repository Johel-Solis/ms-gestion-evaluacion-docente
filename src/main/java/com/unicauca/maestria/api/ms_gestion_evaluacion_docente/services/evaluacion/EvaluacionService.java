package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionDetailDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.ListEvaluacionCursoDto;

public interface EvaluacionService {
    public EvaluacionResponseDto saveEvaluacionDocente(EvaluacionSaveDto evaluacion, BindingResult result);

    //  List<EvaluacionCursoDocente> saveEvaluacionCursoDocente(int periodo, int anio, Long id_evauacion);

    public EvaluacionResponseDto getEvaluacionDocente(int periodo, int anio);

    public List<EvaluacionResponseDto> ListEvaluacionesDocente();

    public EvaluacionDetailDto getMetadataEvaluacionDocente(int periodo, int anio);

    public ListEvaluacionCursoDto getCursosEvaluacionEstudiante(Long idEstudiante);
}
