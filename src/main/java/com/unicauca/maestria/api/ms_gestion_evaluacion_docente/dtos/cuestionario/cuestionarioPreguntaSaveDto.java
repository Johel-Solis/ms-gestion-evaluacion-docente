package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario;

import java.util.List;


import javax.validation.constraints.NotNull;

public class cuestionarioPreguntaSaveDto {
    
    @NotNull
    private long id_cuestionario;
    @NotNull
    private List<Long> id_preguntas;
}
