package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.controllers;

import org.springframework.web.bind.annotation.RestController;



import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.pregunta.PreguntaService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

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


@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/pregunta")
public class PreguntaController {

    private final PreguntaService preguntaService;

    @PostMapping
    public ResponseEntity<PreguntaResponseDto> createPregunta(@Valid @RequestBody PreguntaSaveDto pregunta, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.save(pregunta, result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreguntaResponseDto> getPregunta(@PathVariable Long id) {
        return ResponseEntity.ok(preguntaService.findById(id));
    }

    @PutMapping( "/{id}")
    public ResponseEntity<PreguntaResponseDto> updatePregunta(@PathVariable Long id, @Valid @RequestBody PreguntaSaveDto pregunta, BindingResult result) {
        return ResponseEntity.ok(preguntaService.update(id, pregunta, result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PreguntaResponseDto> deletePregunta(@RequestParam Long id) {
        preguntaService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PreguntaResponseDto>> findAll () {
        return ResponseEntity.ok(preguntaService.findAll());
    }

    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<String> updateEstado(@PathVariable Long id) {
        return ResponseEntity.ok(preguntaService.updateEstado(id));
    }


    @PatchMapping("/eliminar-logico/{id}")
    public ResponseEntity<?> deleteLogic(@PathVariable Long id) {
        preguntaService.deleteLogic(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar/{estado}")
    public ResponseEntity<List<PreguntaResponseDto>> findAllByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(preguntaService.findAllEstado(estado));
    }


    
    
}
