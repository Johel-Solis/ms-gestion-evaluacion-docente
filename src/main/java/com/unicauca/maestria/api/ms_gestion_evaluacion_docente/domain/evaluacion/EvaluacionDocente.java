package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionarioPregunta.Cuestionario;

import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
// @NoArgsConstructor
@Entity
@Table(name = "evaluacion_docente")
public class EvaluacionDocente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int periodo;
    private int anio;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_cuestionario")
    private Cuestionario cuestionario;
    @CreatedDate
    private LocalDateTime fecha_creacion;
    @CreatedDate
    private LocalDateTime fecha_modificacion;

    public EvaluacionDocente() {
        fecha_creacion = LocalDateTime.now();
        fecha_modificacion = LocalDateTime.now();
    }

}
