package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.cuestionario;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioSaveDto;


public interface CuestionarioService {

   public List<CuestionarioResponseDto> findAll();

        public List<CuestionarioResponseDto> findAllEstado(String estado);
    
        public CuestionarioResponseDto findById(Long id);
    
        public CuestionarioResponseDto findByNombre(String nombre);
    
        public CuestionarioResponseDto save(CuestionarioSaveDto cuestionarioSaveDto,BindingResult result);
    
        public CuestionarioResponseDto update(Long id, CuestionarioSaveDto cestionarioSaveDto, BindingResult result);
        
        public String updateEstado(Long id);
    
        public void delete(Long id);

        public void deleteLogic(Long id);
  
} 
