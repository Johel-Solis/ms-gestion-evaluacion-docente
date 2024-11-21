package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.Curso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

        // List<Curso> findAllById(List<Long> idCursos);

        @Query("SELECT c FROM Curso c WHERE c.id IN :ids")
        List<Curso> findAllById(@Param("ids") List<Long> ids);

        // @Query("SELECT c FROM Curso c WHERE c.periodocurso = :periodo AND c.aniocurso
        // = :anio AND c.estado = :estado")
        // List<Curso> findAllByIPeriodoAndAnioAndEstado(int periodo, int anio, int
        // estado);

        // List<Curso> findAllByEstado(int estado);

        List<Curso> findAllByAsignatura(Long idAsignatura);

        Curso findById(long id);

        @Query("SELECT DISTINCT c FROM Curso c JOIN Matricula m ON c.id = m.curso.id WHERE m.id_estudiante IS NOT NULL")
        List<Curso> findCursosWithEstudiantesMatriculados(int periodo, int anio, int estado);

        // @Query("SELECT DISTINCT c FROM Curso c JOIN Matricula m ON c.id = m.curso.id
        // WHERE m.id_estudiante IS NOT NULL AND c.periodo = :periodo AND c.anio = :anio
        // AND c.estado = 1 AND m.estado = 1")
        // List<Curso> findCursosWithMatriculadosAndByPeriodoAnioAndEstado(int periodo,
        // int anio);

        @Query("SELECT DISTINCT c FROM Curso c JOIN Matricula m ON c.id = m.curso.id WHERE c.periodocurso = :periodo AND c.aniocurso = :anio AND c.estado = 1 AND m.estado = 1")
        public List<Curso> findCursosWithMatriculaByAnioAndPeriodocurso(int periodo,
                        int anio);
}