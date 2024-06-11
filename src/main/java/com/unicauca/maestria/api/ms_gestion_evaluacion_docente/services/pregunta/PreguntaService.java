package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.pregunta;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaSaveDto;

public interface PreguntaService {
    
        public List<PreguntaResponseDto> findAll();

        public List<PreguntaResponseDto> findAllEstado(String estado);
    
        public PreguntaResponseDto findById(Long id);
    
        public PreguntaResponseDto findByNombre(String nombre);
    
        public PreguntaResponseDto save(PreguntaSaveDto preguntaSaveDto,BindingResult result);
    
        public PreguntaResponseDto update(Long id, PreguntaSaveDto preguntaSaveDto, BindingResult result);
        
        public String updateEstado(Long id);
    
        public void delete(Long id);

        public void deleteLogic(Long id);

        // public boolean existsByNombre(String nombre);

}
