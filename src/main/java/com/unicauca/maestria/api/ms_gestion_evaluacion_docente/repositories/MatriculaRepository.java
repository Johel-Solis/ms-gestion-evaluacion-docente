package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Matricula;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.Curso;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findAllByAnioAndPeriodo(int anio, int periodo);

    @Query("select distinct m.curso.id from Matricula m where m.anio = ?1 and m.periodo = ?2 and m.estado = 1")
    public List<Long> findIdCursosByAnioAndPeriodo(int anio, int periodo);

    @Query("select distinct m.curso from Matricula m where m.anio = ?1 and m.periodo = ?2 and m.estado = 1")
    public List<Curso> findCursosByAnioAndPeriodo(int anio, int periodo);

    @Query("select count(m.id) from Matricula m where m.curso.id = ?1 and m.estado = 1")
    public int countEstudiantesMatriculados(Long idCurso);

    // cantidad de estudiantes matriculados en un periodo y anio
    @Query("select count(m.id) from Matricula m where m.anio = ?1 and m.periodo = ?2 and m.estado = 1")
    public int countEstudiantesMatriculados(int anio, int periodo);

}
