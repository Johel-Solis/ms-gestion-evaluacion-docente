package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario;


import java.util.List;


import javax.validation.constraints.NotNull;

import lombok.Builder;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuestionarioPreguntaSaveDto {
    
    @NotNull
    private Long idCuestionario;

    private List<Long> idPreguntas;

    // public CuestionarioPreguntaSaveDto() {
    //     id_preguntas = new ArrayList<>();
    // }

}
