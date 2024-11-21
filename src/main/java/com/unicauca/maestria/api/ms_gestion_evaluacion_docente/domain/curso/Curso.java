package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Matricula;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.asignatura.Asignatura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cursos")
public class   Curso {
    @Id
    private Long id;
    private String grupocurso;
    private int periodocurso;
    private int aniocurso;
    private String horariocurso;
    private String saloncurso;
    private int estado;

    @ManyToOne
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;

    // Relación con CursoDocente (opcional, si necesitas acceder a los docentes desde Curso)
    @OneToMany(mappedBy = "curso")
    private List<CursoDocente> cursoDocentes;
    
    // Relación con Matricula (opcional, si necesitas acceder a las matrículas desde Curso)
    @OneToMany(mappedBy = "curso")
    private List<Matricula> matriculas;
}
