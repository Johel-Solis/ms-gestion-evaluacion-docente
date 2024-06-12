package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.common.enums.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "cuestionario")
public class Cuestionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String observacion;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private Integer cantidad_preguntas;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime fecha_creacion;


    public String getFecha_creacionAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        return fecha_creacion.format(formatter);
    }

    public Cuestionario() {
        estado = Estado.ACTIVO;
    }

    
}
