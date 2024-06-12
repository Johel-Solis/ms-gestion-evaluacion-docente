package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.common.enums.Estado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor @Builder
public class CuestionarioSaveDto { 

    @NotBlank
    @Valid
    private String nombre;
    @Size(max = 250)
    private String observacion;
    private Estado  estado;

    public CuestionarioSaveDto() {
        estado = Estado.ACTIVO;
    }   
    
}
