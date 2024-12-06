package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.asignaturaCursos.AsignaturaResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.docente.DocenteResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionDetailDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionEstadisticaCursoDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionEstadisticaDocenteDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionRespuetaSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.ListEvaluacionCursoDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionService;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionReporte.EvaluacionReporteService;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionRespuesta.RespuestaEstudianteService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/evaluacion")
public class EvaluacionController {

    // private final MatriculaService matriculaService;
    // private final CursoService cursoService;
    private final EvaluacionService evaluacionService;
    private final RespuestaEstudianteService respuestaEstudianteService;
    private final EvaluacionReporteService evaluacionReporteService;

    @GetMapping("/metadata")
    public ResponseEntity<EvaluacionDetailDto> getEvaluacionDetail(
            @RequestParam int anio,
            @RequestParam int periodo) {
        EvaluacionDetailDto evaluacionDetailDto = evaluacionService.getMetadataEvaluacionDocente(periodo, anio);

        if (evaluacionDetailDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evaluacionDetailDto);

    }

    @PostMapping
    public ResponseEntity<EvaluacionResponseDto> createEvaluacionDocente(
            @Valid @RequestBody EvaluacionSaveDto evaluacion, BindingResult result) {
        EvaluacionResponseDto entity = evaluacionService.saveEvaluacionDocente(evaluacion, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(entity);
        }
        if (entity == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(entity);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id) {
        String entity = evaluacionService.putEstadoEvaluacion(id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/list")
    public ResponseEntity<List<EvaluacionResponseDto>> findAll() {
        return ResponseEntity.ok(evaluacionService.ListEvaluacionesDocente());
    }

    @GetMapping("/")
    public ResponseEntity<EvaluacionResponseDto> getEvaluacionDocente(
            @RequestParam int anio,
            @RequestParam int periodo) {
        EvaluacionResponseDto entity = evaluacionService.getEvaluacionDocente(periodo, anio);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/cursosEvaluacion/")
    public ResponseEntity<ListEvaluacionCursoDto> getEvaluacionCursosEstudiante(@RequestParam Long id_estudiante) {
        ListEvaluacionCursoDto ecvdto = evaluacionService.getCursosEvaluacionEstudiante(id_estudiante);
        return ResponseEntity.ok(ecvdto);
    }

    @PostMapping("/respuesta")
    public ResponseEntity<String> postMethodName(@RequestBody EvaluacionRespuetaSaveDto evaluacionRespuesta) {
        System.out.println("EvaluacionRespuetaSaveDto: " + evaluacionRespuesta + "\n\n");
        if (respuestaEstudianteService.saveRespuestaEstudiante(evaluacionRespuesta)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/asignatura/")
    public ResponseEntity<List<AsignaturaResponseDto>> getAsignaturasEvaluacion(@RequestParam Long id_evaluacion) {
        List<AsignaturaResponseDto> asignaturas = evaluacionService.getListAsignaturaByEvaluacion(id_evaluacion);
        return ResponseEntity.ok(asignaturas);
    }

    @GetMapping("/asignatura/docente/")
    public ResponseEntity<List<DocenteResponseDto>> getDocenteAsignaturaEvaluacion(@RequestParam Long id_evaluacion,
            @RequestParam Long id_asignatura) {
        List<DocenteResponseDto> docentes = evaluacionService.getListDocenteByAsignaturaEvaluacion(id_evaluacion,
                id_asignatura);
        return ResponseEntity.ok(docentes);
    }

    @GetMapping("/estadistica/docente/")
    public ResponseEntity<EvaluacionEstadisticaDocenteDto> getEstadisticaDocente(@RequestParam Long id_evaluacion,
            @RequestParam Long id_asignatura,
            @RequestParam Long id_docente) {
        EvaluacionEstadisticaDocenteDto estadistica = evaluacionService.getEstadisticaDocente(id_evaluacion,
                id_asignatura, id_docente);
        // ResponseEntity.ok(evaluacionService.getEstadisticaDocente(idasignatura,
        // docente));

        return ResponseEntity.ok(estadistica);
    }

    @GetMapping("/estadistica/")
    public ResponseEntity<List<EvaluacionEstadisticaCursoDto>> getEstadisticaEvaluacion(
            @RequestParam Long id_evaluacion) {
        List<EvaluacionEstadisticaCursoDto> estadisticas = evaluacionService.getEvaluacionEstadistica(id_evaluacion);
        // ResponseEntity.ok(evaluacionService.getEstadisticaCursos(id_evaluacion));
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/estadistica/resportes/")
    public void obtenerReporteEvaluacionDocente(
            @RequestParam Long id_evaluacion,
            HttpServletResponse response)
            throws Exception {
        evaluacionReporteService.getReporteEvaluacionGeneral(id_evaluacion, response);

    }
}
