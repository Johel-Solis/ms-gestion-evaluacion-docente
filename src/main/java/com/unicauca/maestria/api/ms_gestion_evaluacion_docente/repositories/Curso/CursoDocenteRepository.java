package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.Curso;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.Param;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.CursoDocente;

public interface CursoDocenteRepository extends JpaRepository<CursoDocente, Long> {

    Optional<CursoDocente> findById(Long id);
    // List<CursoDocente> findAllById(List<Long> idCursos);

    @Query("SELECT cd FROM CursoDocente cd WHERE cd.curso.id = :idCurso")
    List<CursoDocente> findAllByIdCurso(Long idCurso);

    // @Query("SELECT cd FROM CursoDocente cd WHERE cd.curso.id IN :idCursos")
    // List<CursoDocente> findAllByCursoIds(@Param("idCursos") List<Long> idCursos);

    // @Query("SELECT cd FROM CursoDocente cd JOIN Curso c JOIN Matricula m ON c.id = m.curso.id and cd.curso.id =c.curso.id WHERE c.periodocurso = :periodo AND c.aniocurso = :anio AND c.estado = 1 AND m.estado = 1")
    // public List<CursoDocente> findCursosDocentesByAnioAndPeriodocurso(int periodo,
    //         int anio);

    // @Query("SELECT cd FROM CursoDocente cd JOIN Curso c JOIN Matricula m ON c.id = m.curso.id WHERE c.periodocurso = :periodo AND c.aniocurso = :anio AND c.estado = 1 AND m.estado = 1")
    // public List<CursoDocente> findCursosDocentesByAnioAndPeriodocurso(int periodo, int anio);
    @Query("SELECT cd FROM CursoDocente cd " +
       "JOIN cd.curso c " +
       "JOIN Matricula m ON m.curso.id = c.id " +
       "WHERE c.periodocurso = :periodo " +
       "AND c.aniocurso = :anio " +
       "AND c.estado = 1 " +
       "AND m.estado = 1")
    public List<CursoDocente> findCursosDocentesByAnioAndPeriodocurso(@Param("periodo") int periodo, @Param("anio") int anio);

}
