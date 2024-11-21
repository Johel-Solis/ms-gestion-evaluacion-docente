package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Docente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionCursoDocente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "curso_docente")
public class CursoDocente {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "id_docente")
    private Docente docente;

    @OneToMany(mappedBy = "cursoDocente")
    private List<EvaluacionCursoDocente> evaluacionesCursoDocente;

}
