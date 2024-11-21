package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionDetailDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionRespuetaSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.ListEvaluacionCursoDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionService;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionRespuesta.RespuestaEstudianteService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/evaluacion")
public class EvaluacionController {

    // private final MatriculaService matriculaService;
    // private final CursoService cursoService;
    private final EvaluacionService evaluacionService;
    private final RespuestaEstudianteService respuestaEstudianteService;

    @GetMapping("/metadata")
    public ResponseEntity<EvaluacionDetailDto> getEvaluacionDetail(
            @RequestParam int anio,
            @RequestParam int periodo) {

        System.out.println("anio: " + anio);
        System.out.println("periodo: " + periodo);
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

    //NO IMPLEMENTADO
    @GetMapping("/cursosEvaluacion/")
    public ResponseEntity<ListEvaluacionCursoDto> getEvaluacionCursosEstudiante(@RequestParam Long id_estudiante) {
        ListEvaluacionCursoDto ecvdto = evaluacionService.getCursosEvaluacionEstudiante(id_estudiante);
        return ResponseEntity.ok(ecvdto);
    }
    

    
    @PostMapping("/respuesta")
    public ResponseEntity<String> postMethodName(@RequestBody EvaluacionRespuetaSaveDto evaluacionRespuesta) {
        System.out.println("EvaluacionRespuetaSaveDto: " + evaluacionRespuesta+ "\n\n");
        if(respuestaEstudianteService.saveRespuestaEstudiante(evaluacionRespuesta)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();

    
    }
    
    //NO IMPLEMENTADO
    @GetMapping("/estaditica/docente/{idEvaluacion}/")
    public ResponseEntity<?> getEstadisticaDocente(@RequestParam Long id_evaluacion,@RequestParam Long idasignatura, @RequestParam Long docente) {
        // return ResponseEntity.ok(evaluacionService.getEstadisticaDocente(idasignatura, docente));
        return ResponseEntity.ok().build();
    }

    //NO IMPLEMENTADO
    @GetMapping("/estaditica/cursos/{idEvaluacion}")
    public ResponseEntity<?> getEstadisticaCursos(@RequestParam Long id_evaluacion) {
        // return ResponseEntity.ok(evaluacionService.getEstadisticaCursos(id_evaluacion));
        return ResponseEntity.ok().build();
    }
    
    
}
