package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos.AsignaturaResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.docente.DocenteResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionDetailDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionEstadisticaCursoDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionEstadisticaDocenteDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.ListEvaluacionCursoDto;

public interface EvaluacionService {
    public EvaluacionResponseDto saveEvaluacionDocente(EvaluacionSaveDto evaluacion, BindingResult result);

    // List<EvaluacionCursoDocente> saveEvaluacionCursoDocente(int periodo, int
    // anio, Long id_evauacion);

    public EvaluacionResponseDto getEvaluacionDocente(int periodo, int anio);

    public List<EvaluacionResponseDto> ListEvaluacionesDocente();

    public EvaluacionDetailDto getMetadataEvaluacionDocente(int periodo, int anio);

    public ListEvaluacionCursoDto getCursosEvaluacionEstudiante(Long idEstudiante);

    public List<EvaluacionEstadisticaCursoDto> getEvaluacionEstadistica(Long idEvaluacion);

    public EvaluacionEstadisticaDocenteDto getEstadisticaDocente(Long idEvaluacion, Long idAsignatura, Long idDocente);

    public List<AsignaturaResponseDto> getListAsignaturaByEvaluacion(Long idEvaluacion);

    public List<DocenteResponseDto> getListDocenteByAsignaturaEvaluacion(Long idEvaluacion, Long idAsignatura);

}
