package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionDocente;

public interface EvaluacionRepository extends JpaRepository<EvaluacionDocente, Long> {

    EvaluacionDocente findByPeriodoAndAnioAndEstado(Integer periodo, int anio, String estado);

    EvaluacionDocente findByEstado(String estado);

    List<EvaluacionDocente> findAllByEstado(String estado);

}
