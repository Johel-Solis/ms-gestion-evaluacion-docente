package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.Curso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne
    // @JoinColumn(name = "id_estudiante")

    private Long id_estudiante;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    private int periodo;
    private int anio;
    private int estado;

    private LocalDateTime fecha_creacion;

}
