package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.asignatura;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "areas_formacion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaFormacion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAreaFormacion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

}