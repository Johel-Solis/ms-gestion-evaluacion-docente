package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.pregunta;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Pregunta;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long>{

    List<Pregunta> findAllByEstado(String estado);
    
    public  boolean existsByNombre(String nombre) ;  

    Optional<Pregunta> findByNombre(String nombre);

    Optional<Pregunta> findById(Long id);
    
}
