package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.asignatura;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "asignaturas")
public class Asignatura {

    @Id
    private Long id;
    private Long codigo_asignatura;
    private String estado_asignatura;
    private LocalDateTime fecha_aprobacion;
    private String nombre_asignatura;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_formacion")
    private AreaFormacion areaFormacion;

    private String objetivo_asignatura;
    private String tipo_asignatura;
    private LocalDateTime fecha_creacion;

}
