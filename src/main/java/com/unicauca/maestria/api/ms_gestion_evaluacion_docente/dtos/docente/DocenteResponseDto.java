package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.docente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class DocenteResponseDto {

    private Long id;
    private String codigo;
    private String nombre;
    private String apellido;
    private String correo;
    // private String estado;

}
