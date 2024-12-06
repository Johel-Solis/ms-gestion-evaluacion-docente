package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionReporte;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface EvaluacionReporteService {

    void getReporteEvaluacionGeneral(Long idEvaluacion, HttpServletResponse response) throws IOException;

    // public void getReporteEvaluacionDocente(Long idEvaluacion);

}
