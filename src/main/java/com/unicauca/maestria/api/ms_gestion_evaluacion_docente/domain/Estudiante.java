package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id 
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_persona")
	private Persona persona;
	private String codigo;
	private String ciudadResidencia; 
}
