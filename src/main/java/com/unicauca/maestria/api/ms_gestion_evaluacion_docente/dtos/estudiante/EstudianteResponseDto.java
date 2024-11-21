package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.estudiante;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstudianteResponseDto {
        private Long id;
        private String codigo;
        private String ciudadResidencia;
        private String nombre;
        private String apellido;
        
}
