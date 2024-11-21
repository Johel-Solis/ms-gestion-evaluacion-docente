package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.curso;

import java.util.List;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.Curso;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.CursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos.CursoResponseDto;

public interface CursoService {

        // public List<CursoResponseDto> findAll();

        // public List<CursoResponseDto> findAllByAsignatura(Long idAsignatura);

        // public CursoResponseDto findById(long id);

        List<CursoResponseDto> CursosConEstudiantesMatriculados(int anio, int periodo);

        List<Curso> getCursosByAnioPeriodo(int anio, int periodo);

        List<CursoDocente> getCursosDocentes(Long idCurso);

        public List<CursoDocente> getCursosDocentesByAnioPeriodo(int anio, int periodo);

}
