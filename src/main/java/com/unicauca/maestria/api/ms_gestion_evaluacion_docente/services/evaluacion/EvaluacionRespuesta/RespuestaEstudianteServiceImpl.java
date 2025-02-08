package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionRespuesta;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Estudiante;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionarioPregunta.Pregunta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionCursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionObservacion;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionRespuesta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionRespuetaSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.RespuestaEstudianteDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.cuestionario.CuestionarioPreguntaRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.estudiante.EstudianteRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionCursoDocenteRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionObservacionRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionRespuestaRepositry;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.pregunta.PreguntaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RespuestaEstudianteServiceImpl implements RespuestaEstudianteService {

        private final EvaluacionCursoDocenteRepository evaluacionCursoDocenteRepository;
        private final EstudianteRepository estudianteRepository;
        private final PreguntaRepository preguntaRepository;
        private final CuestionarioPreguntaRepository cuestionarioPreguntaRepository;
        private final EvaluacionRespuestaRepositry respuestaEstudianteRepository;
        private final EvaluacionObservacionRepository evaluacionObservacionRepository;

        public boolean saveRespuestaEstudiante(EvaluacionRespuetaSaveDto evaluacionRespuetaSaveDto) {
                // Obtener la evaluación curso docente
                EvaluacionCursoDocente evaluacionCursoDocente = evaluacionCursoDocenteRepository.findById(
                                evaluacionRespuetaSaveDto.getIdEvaluacionCursoDocente())
                                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

                // Obtener el estudiante
                Estudiante estudiante = estudianteRepository.findById(evaluacionRespuetaSaveDto.getIdEstudiante())
                                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

                List<Pregunta> preguntas = validarPreguntas(evaluacionRespuetaSaveDto.getRespuestas(),
                                evaluacionCursoDocente.getEvaluacion());
                if (preguntas.size() != evaluacionRespuetaSaveDto.getRespuestas().size()) {
                        throw new RuntimeException("Preguntas no encontradas");

                }

                // Guardar cada respuesta
                List<EvaluacionRespuesta> respuestas = evaluacionRespuetaSaveDto.getRespuestas().stream()
                                .map(dto -> {
                                        Pregunta pregunta = preguntaRepository.findById(dto.getIdPregunta())
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        "Pregunta no encontrada"));
                                        return EvaluacionRespuesta.builder()
                                                        .evaluacionCursoDocente(evaluacionCursoDocente)
                                                        .estudiante(estudiante)
                                                        .pregunta(pregunta)
                                                        .valorRespuesta(dto.getValor())
                                                        .build();
                                })
                                .collect(Collectors.toList());

                List<EvaluacionRespuesta> respuestasGuardadas = respuestaEstudianteRepository.saveAll(respuestas);
                if (respuestasGuardadas.size() != respuestas.size()) {
                        throw new RuntimeException("Error al guardar las respuestas");
                }

                EvaluacionObservacion eObservacion = new EvaluacionObservacion();
                eObservacion.setEstudiante(estudiante);
                eObservacion.setEvaluacionCursoDocente(evaluacionCursoDocente);
                eObservacion.setObservacion(evaluacionRespuetaSaveDto.getObservacion());
                evaluacionObservacionRepository.save(eObservacion);

                return true;
        }

        private List<Pregunta> validarPreguntas(List<RespuestaEstudianteDto> respuestas, EvaluacionDocente evaluacion) {
                List<Pregunta> preguntas = cuestionarioPreguntaRepository
                                .findAllByIdCuestionario(evaluacion.getCuestionario().getId());
                return preguntas.stream()
                                .filter(p -> respuestas.stream().anyMatch(r -> r.getIdPregunta().equals(p.getId())))
                                .collect(Collectors.toList());

        }
}
