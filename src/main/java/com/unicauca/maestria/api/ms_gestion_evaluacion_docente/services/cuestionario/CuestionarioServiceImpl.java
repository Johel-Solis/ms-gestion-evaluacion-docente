package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.cuestionario;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.common.enums.Estado;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.Pregunta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionario.Cuestionario;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.cuestionario.CuestionarioPregunta;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioPreguntaSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.cuestionario.CuestionarioSaveDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.CamposUnicosDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.pregunta.PreguntaResponseDto;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.exceptions.FieldErrorException;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.exceptions.FieldUniqueException;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.exceptions.ResourceNotFoundException;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.cuestionario.CuestionarioResponseMapper;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.cuestionario.CuestionarioSaveMapper;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.mappers.pregunta.PreguntaResponseMapper;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.cuestionario.CuestionarioPreguntaRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.cuestionario.CuestionarioRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.pregunta.PreguntaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CuestionarioServiceImpl implements CuestionarioService {

    private final InformacionUnicaCuestionario informacionUnicaCuestionario;
    private final CuestionarioRepository cuestionarioRepository;
    private final CuestionarioPreguntaRepository cuestionarioPreguntaRepository;
    private final CuestionarioResponseMapper cuestionarioResponseMapper;
    private final CuestionarioSaveMapper cuestionarioSaveMapper;
    private final PreguntaRepository preguntaRepository;
    private final PreguntaResponseMapper preguntaResponseMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CuestionarioResponseDto> findAll() {
        System.out.println("findAll");
        return cuestionarioRepository.findAll()
                .stream()
                .map(this::crearCuestionarioResposeDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuestionarioResponseDto> findAllEstado(String estado) {
        return cuestionarioRepository.findAllByEstado(estado)
                .stream()
                .map(this::crearCuestionarioResposeDto).toList();

    }

    @Override
    @Transactional(readOnly = true)
    public CuestionarioResponseDto findById(Long id) {
        return cuestionarioRepository.findById(id)
                .map(this::crearCuestionarioResposeDto)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public CuestionarioResponseDto findByNombre(String nombre) {
        return cuestionarioRepository.findByNombre(nombre)
                .map(this::crearCuestionarioResposeDto)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrada"));

    }

    @Override
    @Transactional
    public CuestionarioResponseDto save(CuestionarioSaveDto cuestionarioSaveDto, BindingResult result) {

        System.out.println("save");

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        Map<String, String> validacionCampoUnico = validacionCampoUnico(obtenerCamposUnicos(cuestionarioSaveDto), null);
        if (!validacionCampoUnico.isEmpty()) {
            throw new FieldUniqueException(validacionCampoUnico);
        }

        Cuestionario cuestionario = cuestionarioRepository.save(cuestionarioSaveMapper.toEntity(cuestionarioSaveDto));
        return cuestionarioResponseMapper.toDto(cuestionario);
    }

    @Override
    @Transactional
    public CuestionarioResponseDto update(Long id, CuestionarioSaveDto cuestionarioSaveDto, BindingResult result) {

        if (result.hasErrors()) {
            throw new FieldErrorException(result);
        }

        Cuestionario cuestionario = cuestionarioRepository.findById(id)
                .orElseThrow(() -> new ValidationException("cuestionario no encontrada con el id: " + id));

        Map<String, String> validacionCampoUnico = validacionCampoUnico(obtenerCamposUnicos(cuestionarioSaveDto),
                informacionUnicaCuestionario.apply(cuestionarioSaveDto));
        if (!validacionCampoUnico.isEmpty()) {
            throw new FieldUniqueException(validacionCampoUnico);
        }

        cuestionario = cuestionarioSaveMapper.toEntity(cuestionarioSaveDto);
        cuestionario.setId(id);

        return cuestionarioResponseMapper.toDto(cuestionarioRepository.save(cuestionario));
    }

    @Override
    @Transactional
    public String updateEstado(Long id) {
        Cuestionario cuestionario = cuestionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cuestionario no encontrada"));
        Estado estado = cuestionario.getEstado() == Estado.ACTIVO ? Estado.INACTIVO : Estado.ACTIVO;
        cuestionario.setEstado(estado);
        cuestionarioRepository.save(cuestionario);
        return "Estado actualizado";
    }

    @Override
    @Transactional
    public CuestionarioResponseDto addPreguntaCuestionario(CuestionarioPreguntaSaveDto cuestionarioPregunta) {
        System.out.println("addPreguntaCuestionario");
        System.out.println(cuestionarioPregunta);
        System.out.println(cuestionarioPregunta.getIdCuestionario());

        System.out.println(cuestionarioPregunta.getIdPreguntas().size());
        List<Long> idsPreguntas = cuestionarioPregunta.getIdPreguntas();

    
    
        Cuestionario cuestionario = cuestionarioRepository.findById(cuestionarioPregunta.getIdCuestionario())
                .orElseThrow(() -> new ResourceNotFoundException("cuestionario no encontrada"));

        List<Long> idsPreguntasBD = cuestionarioPreguntaRepository.findAllIdByIdCuestionario(cuestionario.getId());
                
        // List<Long> idsPreguntas = cuestionarioPregunta.getIdPreguntas();
        // List<LineaInvestigacion> lineasInvestigacion = expertoLineaInvestigacionRepository
        //         .findAllLineasInvByIdExperto(expertoBD.getId());

        List<Long> idsPreguntasAEliminar = idsPreguntasBD.stream()
                .filter(IdPreguntaDB -> !idsPreguntas.contains(IdPreguntaDB)).toList();

        List<Long> idsPreguntasAsignar = idsPreguntas.stream()
                .filter(idPregunta -> !idsPreguntasBD.contains(idPregunta)).toList();

        asignarPreguntasCuestionario(cuestionario, idsPreguntasAsignar);

        for (Long idPregunta : idsPreguntasAEliminar) {
            cuestionarioPreguntaRepository.deleteByIdCuestionarioAndIdPregunta(cuestionario.getId(), idPregunta);

        }
        List<Long> idsP = cuestionarioPreguntaRepository.findAllIdByIdCuestionario(cuestionario.getId());
        cuestionario.setCantidad_preguntas(idsP.size());
        cuestionarioRepository.save(cuestionario);

        return crearCuestionarioResposeDto(cuestionario);
    }

    @Override
    @Transactional
    public void deleteLogic(Long id) {
        Cuestionario cuestionario = cuestionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cuestionario no encontrada"));
        cuestionario.setEstado(Estado.INACTIVO);
        cuestionarioRepository.save(cuestionario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cuestionarioRepository.deleteById(id);
    }

    // @Override
    // @Transactional(readOnly = true)
    // public boolean existsByNombre(String nombre) {
    // return preguntaRepository.existsByNombre(nombre);
    // }

    private CamposUnicosDto obtenerCamposUnicos(CuestionarioSaveDto cuestionarioSaveDto) {
        return informacionUnicaCuestionario.apply(cuestionarioSaveDto);
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

    private CuestionarioResponseDto crearCuestionarioResposeDto(Cuestionario cuestionario) {
        List<PreguntaResponseDto> preguntas = preguntaResponseMapper
                .toDtoList(cuestionarioPreguntaRepository.findAllByIdCuestionario(cuestionario.getId()));

        CuestionarioResponseDto cuestionarioResponseDto = cuestionarioResponseMapper.toDto(cuestionario);
        cuestionarioResponseDto.setPreguntas(preguntas);
        return cuestionarioResponseDto;
    }

    private void asignarPreguntasCuestionario(Cuestionario cuestionario, List<Long> idsPreguntas) {
        List<Pregunta> lineasInvestigacion = BuscarPreguntasCuestionario(idsPreguntas);
        lineasInvestigacion.forEach(li -> {
            CuestionarioPregunta cuestionarioPregunta = CuestionarioPregunta.builder()
                    .cuestionario(cuestionario)
                    .pregunta(li)
                    .build();
            cuestionarioPreguntaRepository.save(cuestionarioPregunta);
        });
    }

    private List<Pregunta> BuscarPreguntasCuestionario(List<Long> idsPreguntas) {
        return preguntaRepository.findAllById(idsPreguntas);
    }

}
