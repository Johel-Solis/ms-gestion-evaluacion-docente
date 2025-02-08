package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionObservacion;;

public interface EvaluacionObservacionRepository extends JpaRepository<EvaluacionObservacion, Long> {

    @Query("SELECT eo.observacion FROM EvaluacionObservacion eo WHERE eo.evaluacionCursoDocente.id = ?1")
    public List<String> findByEvaluacionCursoDocenteId(Long id);
}
