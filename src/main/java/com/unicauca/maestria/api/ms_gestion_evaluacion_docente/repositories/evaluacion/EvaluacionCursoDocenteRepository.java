package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.asignatura.Asignatura;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionCursoDocente;

public interface EvaluacionCursoDocenteRepository extends JpaRepository<EvaluacionCursoDocente, Long> {

    @Query("SELECT ecd FROM EvaluacionCursoDocente ecd WHERE ecd.evaluacion.id = :idEvaluacion")
    List<EvaluacionCursoDocente> findAllByIdEvaluacion(Long idEvaluacion);

    @Query("SELECT DISTINCT ecd.asignatura FROM EvaluacionCursoDocente ecd WHERE ecd.evaluacion.id = :evaluacionId")
    List<Asignatura> findDistinctAsignaturasByEvaluacionId(@Param("evaluacionId") Long evaluacionId);

    
    @Query("SELECT COUNT(DISTINCT ecd.asignatura) FROM EvaluacionCursoDocente ecd WHERE ecd.evaluacion.id = :idEvaluacion")
    int countAsignaturasByEvaluacion(Long idEvaluacion);
    
    
    @Query("SELECT ecd FROM EvaluacionCursoDocente ecd WHERE ecd.cursoDocente.id = :idCursoDocente AND ecd.evaluacion.id = :idEvaluacion")
    EvaluacionCursoDocente findByCursoDocenteAndEvaluacion(@Param("idCursoDocente") Long idCursoDocente, @Param("idEvaluacion") Long idEvaluacion);
    
    // @Query("SELECT COUNT(DISTINCT ecd.asignatura) FROM EvaluacionCursoDocente ecd WHERE ecd.evaluacion.id = :idEvaluacion")
    // int countAsignaturasByEvaluacion(Long idEvaluacion);

    // List<EvaluacionCursoDocente> findAllById

    
}
