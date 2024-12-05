package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionCursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionRespuesta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PromedioRespuestaDTO;

public interface EvaluacionRespuestaRepositry extends JpaRepository<EvaluacionRespuesta, Long> {

        @Query("SELECT DISTINCT er.evaluacionCursoDocente FROM EvaluacionRespuesta er WHERE er.estudiante.id = :idEstudiante")
        List<EvaluacionCursoDocente> findDistinctEvaluacionCursoDocenteByEstudianteId(
                        @Param("idEstudiante") Long idEstudiante);

        // seleccionar todas las respuesta de un estudiante a una evaluacion
        @Query("SELECT er FROM EvaluacionRespuesta er WHERE er.estudiante.id =?1 AND er.evaluacionCursoDocente.id = ?2")
        List<EvaluacionRespuesta> findAllByEstudianteAndEvaluacionCursoDocente(Long idEstudiante,
                        Long idEvaluacionCursoDocente);

        @Query(value = "SELECT AVG(valor_respuesta) " +
                        "FROM evaluacion_respuesta_estudiante ere " +
                        "WHERE ere.id_evaluacion_curso_docente = ?1 " +
                        "AND ere.id_estudiante =?2", nativeQuery = true)
        Double findAverageValorRespuestaByEvaluacionCursoDocenteAndEstudiante(Long idEvaluacionCursoDocente,
                        Long idEstudiante);

        @Query(value = "SELECT AVG(valor_respuesta) " +
                        "FROM evaluacion_respuesta_estudiante ere " +
                        "WHERE ere.id_evaluacion_curso_docente = ?1 ", nativeQuery = true)
        Double findAverageValorRespuestaByEvaluacionCursoDocente(Long idEvaluacionCursoDocente);

        @Query(value = "SELECT COUNT(DISTINCT id_estudiante) " +
                        "FROM evaluacion_respuesta_estudiante ere " +
                        "WHERE ere.id_evaluacion_curso_docente = ?1 ", nativeQuery = true)
        Integer countEstudiantesByEvaluacionCursoDocente(Long idEvaluacionCursoDocente);

        // @Query(value = "SELECT ere.id_evaluacion_curso_docente AS
        // idEvaluacionCursoDocente, " +
        // "ere.id_pregunta AS idPregunta, " +
        // "AVG(ere.valor_respuesta) AS promedioValorRespuesta " +
        // "FROM evaluacion_respuesta_estudiante ere " +
        // "WHERE ere.id_evaluacion_curso_docente = :idEvaluacionCursoDocente " +
        // "GROUP BY ere.id_pregunta", nativeQuery = true)
        // List<PromedioRespuestaDTO> findPromedioRespuestasByEvaluacionCursoDocente(
        // @Param("idEvaluacionCursoDocente") Long idEvaluacionCursoDocente);

        @Query(value = "SELECT ere.id_evaluacion_curso_docente AS idEvaluacionCursoDocente, " +
                        "ere.id_pregunta AS idPregunta, " +
                        "AVG(ere.valor_respuesta) AS promedioValorRespuesta, " +
                        "p.nombre AS nombrePregunta " +
                        "FROM evaluacion_respuesta_estudiante ere " +
                        "JOIN pregunta p ON p.id = ere.id_pregunta " +
                        "WHERE ere.id_evaluacion_curso_docente = :idEvaluacionCursoDocente " +
                        "GROUP BY ere.id_pregunta, p.nombre", nativeQuery = true)
        List<Object[]> findPromedioRespuestasByEvaluacionCursoDocente(
                        @Param("idEvaluacionCursoDocente") Long idEvaluacionCursoDocente);

}
