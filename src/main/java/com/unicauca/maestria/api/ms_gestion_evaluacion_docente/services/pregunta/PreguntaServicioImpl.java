package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.pregunta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.common.enums.Estado;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionarioPregunta.Pregunta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.CamposUnicosDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.exceptions.FieldErrorException;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.exceptions.FieldUniqueException;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.pregunta.PreguntaResponseMapper;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.pregunta.PreguntaSaveMapper;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.pregunta.PreguntaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PreguntaServicioImpl implements PreguntaService {

    private final InformacionUnicaPregunta informacionUnicaPregunta;
    private final PreguntaRepository preguntaRepository;
    private final PreguntaResponseMapper preguntaResponseMapper;
    private final PreguntaSaveMapper preguntaSaveMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaResponseDto> findAll() {
        System.out.println("findAll");
        return preguntaResponseMapper.toDtoList(preguntaRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaResponseDto> findAllEstado(String estado) {
        return preguntaResponseMapper.toDtoList(preguntaRepository.findAllByEstado(estado));
    }

    @Override
    @Transactional(readOnly = true)
    public PreguntaResponseDto findById(Long id) {
        return preguntaResponseMapper.toDto(preguntaRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public PreguntaResponseDto findByNombre(String nombre) {
        Pregunta pregunta = preguntaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        return preguntaResponseMapper.toDto(pregunta);
    }

    @Override
    @Transactional
    public PreguntaResponseDto save(PreguntaSaveDto preguntaSaveDto, BindingResult result) {

        System.out.println("save");

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        Map<String, String> validacionCampoUnico = validacionCampoUnico(obtenerCamposUnicos(preguntaSaveDto), null);
        if (!validacionCampoUnico.isEmpty()) {
            throw new FieldUniqueException(validacionCampoUnico);
        }

        // Pregunta pregunta = preguntaSaveMapper.toEntity(preguntaSaveDto);
        Pregunta pregunta = preguntaRepository.save(preguntaSaveMapper.toEntity(preguntaSaveDto));
        return preguntaResponseMapper.toDto(pregunta);
    }

    @Override
    @Transactional
    public PreguntaResponseDto update(Long id, PreguntaSaveDto preguntaSaveDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Pregunta no encontrada con el id: " + id));

        Map<String, String> validacionCampoUnico = validacionCampoUnico(obtenerCamposUnicos(preguntaSaveDto),
                informacionUnicaPregunta.apply(preguntaSaveDto));
        if (!validacionCampoUnico.isEmpty()) {
            throw new FieldUniqueException(validacionCampoUnico);
        }

        pregunta = preguntaSaveMapper.toEntity(preguntaSaveDto);
        pregunta.setId(id);

        return preguntaResponseMapper.toDto(preguntaRepository.save(pregunta));
    }

    @Override
    @Transactional
    public String updateEstado(Long id) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada"));
        Estado estado = pregunta.getEstado() == Estado.ACTIVO ? Estado.INACTIVO : Estado.ACTIVO;
        pregunta.setEstado(estado);
        preguntaRepository.save(pregunta);
        return "Estado actualizado";
    }

    @Override
    @Transactional
    public void deleteLogic(Long id) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pregunta no encontrada"));
        pregunta.setEstado(Estado.INACTIVO);
        preguntaRepository.save(pregunta);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        preguntaRepository.deleteById(id);
    }

    // @Override
    // @Transactional(readOnly = true)
    // public boolean existsByNombre(String nombre) {
    // return preguntaRepository.existsByNombre(nombre);
    // }

    private CamposUnicosDto obtenerCamposUnicos(PreguntaSaveDto preguntaSaveDto) {
        return informacionUnicaPregunta.apply(preguntaSaveDto);
    }

    private Map<String, String> validacionCampoUnico(CamposUnicosDto camposUnicos,
            CamposUnicosDto camposUnicosBD) {

        Map<String, Function<CamposUnicosDto, Boolean>> mapCamposUnicos = new HashMap<>();

        mapCamposUnicos.put("nombre",
                dto -> (camposUnicosBD == null || !dto.getNombre().equals(camposUnicosBD.getNombre()))
                        && preguntaRepository.existsByNombre(dto.getNombre()));

        Predicate<Field> existeCampoUnico = campo -> mapCamposUnicos.containsKey(campo.getName());
        Predicate<Field> existeCampoBD = campoBD -> mapCamposUnicos.get(campoBD.getName()).apply(camposUnicos);
        Predicate<Field> campoInvalido = existeCampoUnico.and(existeCampoBD);

        return Arrays.stream(camposUnicos.getClass().getDeclaredFields())
                .filter(campoInvalido)
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toMap(Field::getName, field -> {
                    Object valorCampo = null;
                    try {
                        valorCampo = field.get(camposUnicos);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return mensajeException(field.getName(), valorCampo);
                }));

    }

    private <T> String mensajeException(String nombreCampo, T valorCampo) {
        return "Campo único, ya existe una categoria con la información: " + nombreCampo + ": " + valorCampo;
    }

}
