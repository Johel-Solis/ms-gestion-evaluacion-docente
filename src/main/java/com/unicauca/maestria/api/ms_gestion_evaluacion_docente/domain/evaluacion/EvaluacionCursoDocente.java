package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.common.enums.Estado;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.asignatura.Asignatura;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.curso.CursoDocente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
// @NoArgsConstructor
@Builder
@Entity
@Table(name = "evaluacion_curso_docente")
public class EvaluacionCursoDocente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_curso_docente")
    private CursoDocente cursoDocente;

    @ManyToOne
    @JoinColumn(name = "id_evaluacion")
    private EvaluacionDocente evaluacion;

    @ManyToOne
    @JoinColumn(name = "id_asignatura")
    private Asignatura asignatura;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @CreatedDate
    // @Column(updatable = false)
    private LocalDateTime fecha_creacion;

    @OneToMany(mappedBy = "evaluacionCursoDocente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EvaluacionRespuesta> respuestas;

    @OneToMany(mappedBy = "evaluacionCursoDocente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EvaluacionObservacion> observaciones;

    public EvaluacionCursoDocente() {
        estado = Estado.ACTIVO;
        fecha_creacion = LocalDateTime.now();
    }

    public List<String> getObservacionesListaTexto() {
        List<String> observacionesStrings = new ArrayList<String>();
        if (observaciones == null || observaciones.isEmpty()) {
            return observacionesStrings;
        }
        return observaciones.stream()
                .map(EvaluacionObservacion::getObservacion)
                .filter(obs -> obs != null && !obs.isEmpty())
                .collect(Collectors.toList());
    }
}
