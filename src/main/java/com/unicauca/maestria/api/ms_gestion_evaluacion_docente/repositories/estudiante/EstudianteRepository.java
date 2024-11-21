package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.estudiante;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante,Long> {
    
}
