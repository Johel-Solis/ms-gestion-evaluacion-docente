package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.common.enums.Estado;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionarioPregunta.Cuestionario;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.Curso;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.CursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionRespuesta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionCursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos.AreaFormacionResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos.CursoResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionCursoDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionDetailDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.ListEvaluacionCursoDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.evaluacion.EvaluacionSaveMapper;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.MatriculaRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.Curso.CursoDocenteRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.cuestionario.CuestionarioRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionCursoDocenteRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionRespuestaRepositry;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.curso.CursoService;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
@Service
public class EvaluacionServiceImpl implements EvaluacionService {

    private final CursoService cursoService;
    private final MatriculaRepository matriculaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final EvaluacionCursoDocenteRepository evaluacionCursoDocenteRepository;
    private final EvaluacionSaveMapper evaluacionSaveMapper;
    private final CuestionarioRepository cuestionarioRepository;
    private final EvaluacionRespuestaRepositry evaluacionRespuestaRepository;
    // private final CursoDocenteRepository cursoDocenteRepository;

    @Override
    public EvaluacionResponseDto saveEvaluacionDocente(EvaluacionSaveDto evaluacion, BindingResult result) {
        System.out.println("saveEvaluacionDocente\n\n");
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Error en los datos de la evaluación");
        }
        System.out.println("Validacion de campos\n\n");

        if (!validarCamposEvaluacion(evaluacion)) {
            throw new IllegalArgumentException("Error en los datos de la evaluación");
        }
        EvaluacionDocente eval = evaluacionRepository.findByEstado(Estado.ACTIVO.toString());
        // if (eval != null) {
        // throw new IllegalArgumentException("Ya existe una evaluación activa");
        // }

        System.out.println("guardado en la base de datoos\n\n");
        EvaluacionDocente ev = evaluacionSaveMapper.toEntity(evaluacion);
        Cuestionario cuestionario = cuestionarioRepository.findById(evaluacion.getId_cuestionario()).orElse(null);
        if (cuestionario == null) {
            throw new IllegalArgumentException("Cuestionario no encontrado");
        }
        ev.setCuestionario(cuestionario);
        System.out.println("guardado en la base de datoos\n\n" + ev);
        // EvaluacionDocente evaluacionEntity = evaluacionRepository.save(ev);
        List<EvaluacionCursoDocente> ecds = saveEvaluacionCursoDocente(evaluacion.getPeriodo(), evaluacion.getAnio(),
                eval);

        System.err.println("Evaluaciones guardadas: " + ecds.size());
        return createEvaluacionResponseDto(eval);
    }

    private List<EvaluacionCursoDocente> saveEvaluacionCursoDocente(int periodo, int anio,
            EvaluacionDocente evauacion) {
        List<CursoDocente> cursos_docentes = cursoService.getCursosDocentesByAnioPeriodo(anio, periodo);
        List<EvaluacionCursoDocente> evaluacionesCursoDocente = new ArrayList<>();
        System.out.println("cursos_docentes\n\n" + cursos_docentes.size());
        evauacion.setId((long) 1);
        System.out.println("evauacion\n\n" + evauacion);
        for (CursoDocente cur_D : cursos_docentes) {
            EvaluacionCursoDocente ecd = evaluacionCursoDocenteRepository.save(EvaluacionCursoDocente.builder()
                    .cursoDocente(cur_D)
                    .evaluacion(evauacion)
                    .asignatura(cur_D.getCurso().getAsignatura())
                    .estado(Estado.ACTIVO)
                    .build());
            EvaluacionCursoDocente evaluacionCursoDocente = new EvaluacionCursoDocente();
            evaluacionesCursoDocente.add(evaluacionCursoDocente);
        }
        return evaluacionesCursoDocente;
    }

    @Override
    public EvaluacionResponseDto getEvaluacionDocente(int periodo, int anio) {
        EvaluacionDocente ev = evaluacionRepository.findByPeriodoAndAnio(periodo, anio);
        if (ev == null) {
            // throw new IllegalArgumentException("Evaluación no encontrada");
            return null;
        }
        return createEvaluacionResponseDto(ev);
    }

    @Override
    public List<EvaluacionResponseDto> ListEvaluacionesDocente() {
        return evaluacionRepository.findAll().stream()
                .map(this::createEvaluacionResponseDto)
                .toList();
    }

    public EvaluacionDetailDto getMetadataEvaluacionDocente(int periodo, int anio) {

        List<CursoResponseDto> cursos = cursoService.CursosConEstudiantesMatriculados(anio, periodo);

        int cantidadEstudiantesResgistrados = matriculaRepository.countEstudiantesMatriculados(anio, periodo);
        EvaluacionDetailDto evaluacionDetailDto = maperEvaluacionDetailDto(cursos, cantidadEstudiantesResgistrados);
        return evaluacionDetailDto;
    }

    private EvaluacionDetailDto maperEvaluacionDetailDto(List<CursoResponseDto> cursos,
            int cantidadEstudiantesResgistrados) {
        EvaluacionDetailDto evaluacionDetailDto = new EvaluacionDetailDto();
        evaluacionDetailDto.setCantidadEstudiantesResgistrados(cantidadEstudiantesResgistrados);
        evaluacionDetailDto.setAreasFormacion(agruparCursosPorArea(cursos));

        return evaluacionDetailDto;
    }

    private List<AreaFormacionResponseDto> agruparCursosPorArea(List<CursoResponseDto> cursos) {
        // Agrupar los cursos por idArea y área
        Map<Long, List<CursoResponseDto>> cursosPorArea = cursos.stream()
                .collect(Collectors.groupingBy(CursoResponseDto::getIdArea));

        // Construir la lista de AreaFormacionDto a partir de la agrupación anterior
        List<AreaFormacionResponseDto> areasFormacion = new ArrayList<>();

        for (Map.Entry<Long, List<CursoResponseDto>> entry : cursosPorArea.entrySet()) {
            Long idArea = entry.getKey();
            List<CursoResponseDto> cursosDeArea = entry.getValue();

            // Obtener el nombre del área desde el primer curso de la lista
            String nombreArea = cursosDeArea.isEmpty() ? "" : cursosDeArea.get(0).getArea();

            AreaFormacionResponseDto areaFormacionDto = AreaFormacionResponseDto.builder()
                    .idArea(idArea)
                    .nombre(nombreArea)
                    .cursos(cursosDeArea)
                    .build();

            areasFormacion.add(areaFormacionDto);
        }

        return areasFormacion;
    }

    private boolean validarCamposEvaluacion(EvaluacionSaveDto evaluacion) {
        int currentYear = Year.now().getValue();
        int anio = evaluacion.getAnio();
        int periodo = evaluacion.getPeriodo();

        boolean isAnioValido = anio >= (currentYear - 1) && anio <= (currentYear + 1);
        boolean isPeriodoValido = periodo == 1 || periodo == 2;

        return isAnioValido && isPeriodoValido;
    }

    private EvaluacionResponseDto createEvaluacionResponseDto(EvaluacionDocente evaluacionEntity) {
        int cantidadAsignaturas = evaluacionCursoDocenteRepository
                .countAsignaturasByEvaluacion(evaluacionEntity.getId());
        return EvaluacionResponseDto.builder()
                .id(evaluacionEntity.getId())
                .anio(evaluacionEntity.getAnio())
                .periodo(evaluacionEntity.getPeriodo())
                .nombreCuestionario(evaluacionEntity.getCuestionario().getNombre())
                .cantidadAsignaturas(cantidadAsignaturas)
                .estado(evaluacionEntity.getEstado())
                .build();
    }

    @Override
    public ListEvaluacionCursoDto getCursosEvaluacionEstudiante(Long idEstudiante) {
        // 1. obtener evaluacion docente activa
        System.err.println("getCursosEvaluacionEstudiante\n\n");
        EvaluacionDocente evaluacionDocente = evaluacionRepository.findByEstado(Estado.ACTIVO.toString());
        if (evaluacionDocente == null) {
            throw new IllegalArgumentException("No existe una evaluación activa");
        }
        System.err.println("evaluacionDocente\n\n" + evaluacionDocente.getAnio());

        // 2. obtener cursos del estudiante
        List<Curso> cursos = matriculaRepository.findCursosByAnioAndPeriodo(evaluacionDocente.getAnio(),
                evaluacionDocente.getPeriodo());
        System.err.println("cursos\n\n" + cursos.size());
        // 3. obtener cursos evaluados
        List<EvaluacionCursoDto> cursosEvaluados = new ArrayList<>();

        for (Curso curso : cursos) {

            

            List<CursoDocente> cursoDocentes = curso.getCursoDocentes();
            
            for (CursoDocente cursoDocente : cursoDocentes) {
                EvaluacionCursoDto evaluacionCursoDto = new EvaluacionCursoDto();

                evaluacionCursoDto.setAsignatura(curso.getAsignatura().getNombre_asignatura());
                String docente = cursoDocente.getDocente().getPersona().getNombre() + " "
                        + cursoDocente.getDocente().getPersona().getApellido();

                evaluacionCursoDto.setDocente(docente);

               

                EvaluacionCursoDocente evaluacionCursoDocente = evaluacionCursoDocenteRepository
                        .findByCursoDocenteAndEvaluacion(cursoDocente.getId(), evaluacionDocente.getId());

               

                evaluacionCursoDto.setIdEvaluacionCurso(evaluacionCursoDocente.getId());

                String estado = calculateState(evaluacionCursoDocente.getId(), idEstudiante);
               
                evaluacionCursoDto.setEstado(estado);

                float nota = calculateGrade(evaluacionCursoDocente.getId(), idEstudiante);
               

                evaluacionCursoDto.setNota(nota);
               

                cursosEvaluados.add(evaluacionCursoDto);
            }
        }
        return ListEvaluacionCursoDto.builder()
                .evaluacionesCurso(cursosEvaluados)
                .idCuestionario(evaluacionDocente.getCuestionario().getId())
                .build();
    }

    private float calculateGrade(Long idEvaluacionCursoDocente, Long idEstudiante) {
        List<EvaluacionRespuesta> listEV = evaluacionRespuestaRepository
                .findAllByEstudianteAndEvaluacionCursoDocente(idEstudiante, idEvaluacionCursoDocente);
        if (listEV.size() > 0) {
            float nota = 0;
            for (EvaluacionRespuesta ev : listEV) {
                System.err.println("ev.getValorRespuesta()\n\n" + ev.getValorRespuesta());
                nota += ev.getValorRespuesta();
            }
            return nota / listEV.size();
        }
        return 0;
    }

    private String calculateState(Long idEvaluacionCursoDocente, Long idEstudiante) {
        List<EvaluacionRespuesta> listEV = evaluacionRespuestaRepository
                .findAllByEstudianteAndEvaluacionCursoDocente(idEstudiante, idEvaluacionCursoDocente);
        if (listEV.size() > 0) {
            return "Evaluado";
        }
        return "Pendiente";
    }
}
