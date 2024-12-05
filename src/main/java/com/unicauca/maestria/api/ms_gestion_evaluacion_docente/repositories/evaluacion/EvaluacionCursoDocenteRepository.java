package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.asignatura.Asignatura;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.CursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionCursoDocente;

public interface EvaluacionCursoDocenteRepository extends JpaRepository<EvaluacionCursoDocente, Long> {

    @Query("SELECT ecd FROM EvaluacionCursoDocente ecd WHERE ecd.evaluacion.id = :idEvaluacion")
    List<EvaluacionCursoDocente> findAllByIdEvaluacion(Long idEvaluacion);

    @Query("SELECT DISTINCT ecd.asignatura FROM EvaluacionCursoDocente ecd WHERE ecd.evaluacion.id = :evaluacionId")
    List<Asignatura> findDistinctAsignaturasByEvaluacionId(@Param("evaluacionId") Long evaluacionId);

    @Query("SELECT COUNT(DISTINCT ecd.asignatura) FROM EvaluacionCursoDocente ecd WHERE ecd.evaluacion.id = :idEvaluacion")
    int countAsignaturasByEvaluacion(Long idEvaluacion);

    @Query("SELECT ecd FROM EvaluacionCursoDocente ecd WHERE ecd.cursoDocente.id = :idCursoDocente AND ecd.evaluacion.id = :idEvaluacion")
    EvaluacionCursoDocente findByCursoDocenteAndEvaluacion(@Param("idCursoDocente") Long idCursoDocente,
            @Param("idEvaluacion") Long idEvaluacion);

    // @Query("SELECT COUNT(DISTINCT ecd.asignatura) FROM EvaluacionCursoDocente ecd
    // WHERE ecd.evaluacion.id = :idEvaluacion")
    // int countAsignaturasByEvaluacion(Long idEvaluacion);

    // List<EvaluacionCursoDocente> findAllById

    @Query("SELECT ecd.cursoDocente FROM EvaluacionCursoDocente ecd WHERE ecd.evaluacion.id = :idEvaluacion AND ecd.asignatura.id = :idAsignatura")
    List<CursoDocente> findCursoDocenteByEvaluacionAndAsignatura(@Param("idEvaluacion") Long idEvaluacion,
            @Param("idAsignatura") Long idAsignatura);

    @Query(value = "SELECT ecd.* " +
            "FROM evaluacion_curso_docente ecd INNER JOIN (SELECT cd.id as id_cd FROM curso_docente cd WHERE id_docente =?3) cd1 "
            +
            "ON cd1.id_cd = ecd.id_curso_docente " +
            "WHERE id_evaluacion =?1 and id_asignatura =?2", nativeQuery = true)
    List<EvaluacionCursoDocente> findIdCursoDocenteByEvaluacionAndAsignaturaAndDocente(Long idEvaluacion,
            Long idAsignatura,
            Long idDocente);

}
