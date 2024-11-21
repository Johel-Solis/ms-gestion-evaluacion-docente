package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.curso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.Curso;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.CursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos.CursoResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.docente.DocenteResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.MatriculaRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.Curso.CursoDocenteRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.Curso.CursoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CursoServiceImpl implements CursoService {

        private final CursoRepository cursoRepository;
        private final CursoDocenteRepository cursoDocenteRepository;
        private final MatriculaRepository matriculaRepository;

        // @Override
        // public List<CursoResponseDto> findAll() {
        // return cursoRepository.findAll().stream()
        // .map(this::mapCursoToCursoResponseDto)
        // .collect(Collectors.toList());
        // }

        // @Override
        // public CursoResponseDto findById(long id) {
        // Curso curso = cursoRepository.findById(id);
        // return mapCursoToCursoResponseDto(curso);
        // }

        // @Override
        // public List<CursoResponseDto> findAllByAsignatura(Long idAsignatura) {
        // return cursoRepository.findAllByAsignatura(idAsignatura).stream()
        // .map(this::mapCursoToCursoResponseDto)
        // .collect(Collectors.toList());
        // }

        private CursoResponseDto mapCursoToCursoResponseDto(Curso curso) {
                List<DocenteResponseDto> DocentesResponse = mapDocenteToDocenteResponseDto(
                                curso.getCursoDocentes());
                int cantEstudiantes = curso.getMatriculas().size();

                return CursoResponseDto.builder()
                                .id(curso.getId())
                                .grupoCurso(curso.getGrupocurso())
                                .idArea(curso.getAsignatura().getAreaFormacion().getIdAreaFormacion())
                                .area(curso.getAsignatura().getAreaFormacion().getNombre())
                                .asignatura(curso.getAsignatura().getNombre_asignatura())
                                .cantidadEstudiantes(cantEstudiantes)
                                .codigo(curso.getAsignatura().getCodigo_asignatura())
                                // .estudiantes(cursoEstudianteRepository.findAllByCurso(curso.getId()))
                                .docentes(DocentesResponse)
                                .build();
        }

        private List<DocenteResponseDto> mapDocenteToDocenteResponseDto(List<CursoDocente> cursoDocentes) {
                return cursoDocentes.stream()
                                .map(CursoDocente::getDocente)
                                .map(docente -> DocenteResponseDto.builder()
                                                .id(docente.getId())
                                                .codigo(docente.getCodigo())
                                                .nombre(docente.getPersona().getNombre())
                                                .apellido(docente.getPersona().getApellido())
                                                .correo(docente.getPersona().getCorreoElectronico())
                                                .build())
                                .collect(Collectors.toList());
        }

        public List<CursoDocente> getCursosDocentes(Long idCurso) {
                return cursoDocenteRepository.findAllByIdCurso(idCurso);
        }

        public List<CursoDocente> getCursosDocentesByAnioPeriodo(int anio, int periodo) {
                List<Curso> cursos = cursoRepository.findCursosWithMatriculaByAnioAndPeriodocurso(periodo, anio);
                System.err.println("cursos: " + cursos.size() + "\n\n");
                List<CursoDocente> curosDocentes = new ArrayList<>();
                for (Curso curso : cursos) {
                        curosDocentes.addAll(curso.getCursoDocentes());
                }
                return curosDocentes;
        }
        
        public List<Curso> getCursosByAnioPeriodo(int anio, int periodo) {
                return cursoRepository.findCursosWithMatriculaByAnioAndPeriodocurso(periodo, anio);
        }

        @Override
        public List<CursoResponseDto> CursosConEstudiantesMatriculados(int anio, int periodo) {
                List<Curso> cursos = cursoRepository.findCursosWithMatriculaByAnioAndPeriodocurso(periodo, anio);
                System.err.println("cursos: " + cursos.size());
                System.err.println("cursos: " + cursos.get(0).getMatriculas().size()+"\n\n");	
                return cursos.stream()
                                .map(this::mapCursoToCursoResponseDto)
                                .collect(Collectors.toList());
        }

}
