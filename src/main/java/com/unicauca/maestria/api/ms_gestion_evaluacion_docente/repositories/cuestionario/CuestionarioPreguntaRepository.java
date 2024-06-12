package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.cuestionario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Pregunta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionario.CuestionarioPregunta;

public interface CuestionarioPreguntaRepository extends JpaRepository<CuestionarioPregunta, Long>{
    
    @Query("SELECT cp.id_pregunta.id FROM CuestionarioPregunta cp WHERE cp.id_cuestionario.id = ?1")
    public List<Long> findAllIdByIdCuestionario(Long idCuestionario);

    @Query("SELECT cp.id_cuestionario FROM CuestionarioPregunta cp WHERE cp.id_pregunta = ?1")
    public List<Long> findByIdPregunta(Long idPregunta);

    @Query("SELECT cp.id_pregunta FROM CuestionarioPregunta cp WHERE cp.id_cuestionario.id = ?1")
    public List<Pregunta> findAllByIdCuestionario(Long idCuestionario);


    @Modifying
    @Query("DELETE FROM CuestionarioPregunta cp WHERE cp.id_cuestionario.id = ?1 AND cp.id_pregunta.id = ?2")
    public void deleteByIdCuestionarioAndIdPregunta(Long idCuestionario, Long idPregunta);
}
