package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import javax.validation.constraints.NotNull;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.common.enums.Estado;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
// @NoArgsConstructor
@Builder
public class EvaluacionSaveDto {

    @NotNull
    @Min(1)
    @Max(2)
    private Integer periodo;

    @NotNull
    private int anio;

    @NotNull
    private Long id_cuestionario;

    private Estado estado;

    public EvaluacionSaveDto() {
        estado = Estado.ACTIVO;
    }

}
