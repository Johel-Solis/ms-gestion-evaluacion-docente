package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionCursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionRespuesta;

public interface EvaluacionRespuestaRepositry extends JpaRepository<EvaluacionRespuesta, Long> {
    
     @Query("SELECT DISTINCT er.evaluacionCursoDocente FROM EvaluacionRespuesta er WHERE er.estudiante.id = :idEstudiante")
    List<EvaluacionCursoDocente> findDistinctEvaluacionCursoDocenteByEstudianteId(@Param("idEstudiante") Long idEstudiante);

    // seleccionar todas las respuesta de un estudiante a una evaluacion
    @Query("SELECT er FROM EvaluacionRespuesta er WHERE er.estudiante.id =?1 AND er.evaluacionCursoDocente.id = ?2")
    List<EvaluacionRespuesta> findAllByEstudianteAndEvaluacionCursoDocente( Long idEstudiante,  Long idEvaluacionCursoDocente);
}

