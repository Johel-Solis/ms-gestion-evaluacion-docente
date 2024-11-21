package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.asignatura.Asignatura;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "docentes_asignatura")
public class docente_asignatura {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;

}
