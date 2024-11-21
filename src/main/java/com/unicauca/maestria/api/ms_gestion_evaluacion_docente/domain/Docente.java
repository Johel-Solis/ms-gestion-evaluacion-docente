package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.common.enums.Estado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "docentes")
public class Docente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_persona")
	private Persona persona;

	private String codigo;

	private String facultad;

	private String departamento;

	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private Estado estado;

}
