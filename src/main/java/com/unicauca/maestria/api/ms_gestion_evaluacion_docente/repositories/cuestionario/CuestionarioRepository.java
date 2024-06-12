package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.cuestionario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionario.Cuestionario;


public interface CuestionarioRepository  extends JpaRepository<Cuestionario, Long>{

    List<Cuestionario> findAllByEstado(String estado);

    public  boolean existsByNombre(String nombre) ;

    Optional<Cuestionario> findByNombre(String nombre);

    Optional<Cuestionario> findById(Long id);

    
    
}
