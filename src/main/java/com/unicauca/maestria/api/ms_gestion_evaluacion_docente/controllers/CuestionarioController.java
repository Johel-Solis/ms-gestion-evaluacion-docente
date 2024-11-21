package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioPreguntaSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.cuestionario.CuestionarioService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/cuestionario")
public class CuestionarioController {
      private final CuestionarioService cuestionarioService;

    @PostMapping
    public ResponseEntity<CuestionarioResponseDto> createCuestionario(
            @Valid @RequestBody CuestionarioSaveDto cuestionario, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuestionarioService.save(cuestionario, result));
    }

    @PostMapping("/preguntas")
    public ResponseEntity<CuestionarioResponseDto> addPreguntas(@RequestBody CuestionarioPreguntaSaveDto cuestionarioPregunta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuestionarioService.addPreguntaCuestionario(cuestionarioPregunta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuestionarioResponseDto> getCuestionario(@PathVariable Long id) {
        return ResponseEntity.ok(cuestionarioService.findById(id));
    }

    @PutMapping( "/{id}")
    public ResponseEntity<CuestionarioResponseDto> updateCuestionario(@PathVariable Long id, @Valid @RequestBody CuestionarioSaveDto cuestionario, BindingResult result) {
        return ResponseEntity.ok(cuestionarioService.update(id, cuestionario, result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CuestionarioResponseDto> deleteCuestionario(@RequestParam Long id) {
        cuestionarioService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CuestionarioResponseDto>> findAll () {
        return ResponseEntity.ok(cuestionarioService.findAll());
    }

    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<String> updateEstado(@PathVariable Long id) {
        return ResponseEntity.ok(cuestionarioService.updateEstado(id));
    }


    @PatchMapping("/eliminar-logico/{id}")
    public ResponseEntity<?> deleteLogic(@PathVariable Long id) {
        cuestionarioService.deleteLogic(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar/{estado}")
    public ResponseEntity<List<CuestionarioResponseDto>> findAllByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(cuestionarioService.findAllEstado(estado));
    }

}
