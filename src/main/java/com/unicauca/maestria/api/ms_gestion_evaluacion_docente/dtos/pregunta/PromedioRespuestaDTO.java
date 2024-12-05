package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta;

import lombok.Data;

@Data

public class PromedioRespuestaDTO {

    private Long idEvaluacionCursoDocente;
    private Long idPregunta;
    private Double promedioValorRespuesta;
    private String nombrePregunta;

    // Constructor, Getters y Setters
    public PromedioRespuestaDTO(Long idEvaluacionCursoDocente, Long idPregunta,
            Double promedioValorRespuesta, String nombrePregunta) {
        this.idEvaluacionCursoDocente = idEvaluacionCursoDocente;
        this.idPregunta = idPregunta;
        this.promedioValorRespuesta = promedioValorRespuesta;
        this.nombrePregunta = nombrePregunta;
    }

    PromedioRespuestaDTO() {
    }

    // Getters y setters
    public Long getIdEvaluacionCursoDocente() {
        return idEvaluacionCursoDocente;
    }

    public void setIdEvaluacionCursoDocente(Long idEvaluacionCursoDocente) {
        this.idEvaluacionCursoDocente = idEvaluacionCursoDocente;
    }

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Double getPromedioValorRespuesta() {
        return promedioValorRespuesta;
    }

    public void setPromedioValorRespuesta(Double promedioValorRespuesta) {
        this.promedioValorRespuesta = promedioValorRespuesta;
    }

    public String getNombrePregunta() {
        return nombrePregunta;
    }

    public void setNombrePregunta(String nombrePregunta) {
        this.nombrePregunta = nombrePregunta;
    }
}
