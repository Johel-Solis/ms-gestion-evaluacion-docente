package com.unicauca.maestria.api.ms_gestion_evaluacion_docente.services.evaluacion.EvaluacionReporte;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Service;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.asignatura.Asignatura;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionCursoDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.domain.evaluacion.EvaluacionDocente;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.dtos.evaluacion.EvaluacionEstadisticaCursoDto;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.MatriculaRepository;

import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionCursoDocenteRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionRepository;
import com.unicauca.maestria.api.ms_gestion_evaluacion_docente.repositories.evaluacion.EvaluacionRespuestaRepositry;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EvaluacionReporteServiceimpl implements EvaluacionReporteService {

    private final MatriculaRepository matriculaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final EvaluacionCursoDocenteRepository evaluacionCursoDocenteRepository;

    private final EvaluacionRespuestaRepositry evaluacionRespuestaRepository;

    @Override
    public void getReporteEvaluacionGeneral(Long idEvaluacion, HttpServletResponse response) throws IOException {
        // obtener evaluaciones generales

        EvaluacionDocente evD = evaluacionRepository.findById(idEvaluacion)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada"));

        List<Asignatura> asignaturas = evaluacionCursoDocenteRepository
                .findDistinctAsignaturasByEvaluacionId(idEvaluacion);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Evaluaciones Docente");

        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Evaluación Docente -" + evD.getPeriodo() + "-" + evD.getAnio());
        CellRangeAddress titleRegion = new CellRangeAddress(0, 0, 0, 3); // Fusiona 4 celdas para el título
        sheet.addMergedRegion(titleRegion);

        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14); // Cambiar tamaño de fuente
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

        // Crear un estilo con bordes
        CellStyle borderedCellStyle = createBorderedCellStyle(workbook);

        // Llenar los datos
        int rowNum = 2;
        for (Asignatura asignatura : asignaturas) {

            List<EvaluacionEstadisticaCursoDto> estadisticas = getEstadisticaAsignatura(idEvaluacion,
                    asignatura.getId());

            float notaFinal = calculateGrade(estadisticas);
            // Crear la cabecera de la asignatura
            String[] columnsAsignatura = { "Nombre Asignatura", "Nota Evaluación" };
            createHeaderRow(sheet, columnsAsignatura, rowNum);
            rowNum++; // Moverse a la siguiente fila después de la cabecera

            // Llenar los datos de la asignatura
            Row rowAsignatura = sheet.createRow(rowNum++);
            Cell asignaturaCell = rowAsignatura.createCell(0);
            rowAsignatura.createCell(0).setCellValue(asignatura.getNombre_asignatura());
            asignaturaCell.setCellStyle(borderedCellStyle);

            Cell notaCell = rowAsignatura.createCell(1);
            rowAsignatura.createCell(1).setCellValue(notaFinal);
            notaCell.setCellStyle(borderedCellStyle); // Nota de la asignatura

            // Crear la cabecera de los docentes
            String[] columnsDocentes = { "Docente", "Nota Evaluación", "N° de Evaluaciones" };
            createHeaderRow(sheet, columnsDocentes, rowNum);
            rowNum++; // Moverse a la siguiente fila después de la cabecera

            // Llenar los datos de los docentes
            for (EvaluacionEstadisticaCursoDto ecd : estadisticas) { // Supongo que "getDocentes()" te da la
                                                                     // lista de docentes
                Row rowDocente = sheet.createRow(rowNum++);
                // Cell docenteCell = rowDocente.createCell(0);
                rowDocente.createCell(0).setCellValue(ecd.getDocente());
                // docenteCell.setCellStyle(borderedCellStyle);

                // Cell notaDocCell = rowDocente.createCell(1);
                rowDocente.createCell(1).setCellValue(ecd.getNotaPromedio()); // Nota del docente
                // notaDocCell.setCellStyle(borderedCellStyle);

                // Cell totalCell = rowDocente.createCell(2);
                rowDocente.createCell(2).setCellValue(ecd.getTotalRespondidas() + " de " + ecd.getTotalEvaluaciones());
                // totalCell.setCellStyle(borderedCellStyle);

            }

            // Saltar dos filas antes de comenzar con la siguiente asignatura
            rowNum += 2;
        }

        // Ajustar el tamaño de las columnas
        for (int i = 0; i < 2; i++) { // Ya que solo hay dos columnas por sección (Asignatura/Nota y Docente/Nota)
            sheet.autoSizeColumn(i);
        }

        // Configurar la respuesta HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ReporteEvaluacionGeneral.xlsx");

        // Escribir el archivo Excel en la respuesta HTTP
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // Método auxiliar para crear la fila de encabezado
    private void createHeaderRow(Sheet sheet, String[] columns, int rowNum) {
        Row headerRow = sheet.createRow(rowNum);
        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private CellStyle createBorderedCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        // Establecer borde para todas las direcciones (arriba, abajo, izquierda,
        // derecha)
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        return cellStyle;
    }

    private List<EvaluacionEstadisticaCursoDto> getEstadisticaAsignatura(Long idEvaluacion, Long idAsignatura) {
        // 1. filtrar evaluacion_curso_docente por idEvaluacion
        List<EvaluacionCursoDocente> evaluacionCursoDocentes = evaluacionCursoDocenteRepository
                .findByEvaluacionAndAsignatura(idEvaluacion, idAsignatura);

        List<EvaluacionEstadisticaCursoDto> estadisticas = new ArrayList<>();
        for (EvaluacionCursoDocente evaluacionCursoDocente : evaluacionCursoDocentes) {
            EvaluacionEstadisticaCursoDto estadistica = new EvaluacionEstadisticaCursoDto();
            estadistica.setAsignatura(evaluacionCursoDocente.getAsignatura().getNombre_asignatura());
            estadistica.setDocente(evaluacionCursoDocente.getCursoDocente().getDocente().getPersona().getNombre() + " "
                    + evaluacionCursoDocente.getCursoDocente().getDocente().getPersona().getApellido());
            estadistica.setTotalRespondidas(evaluacionRespuestaRepository
                    .countEstudiantesByEvaluacionCursoDocente(evaluacionCursoDocente.getId()));
            estadistica.setTotalEvaluaciones(matriculaRepository
                    .countEstudiantesByCursoAndAnioAndPeriodo(evaluacionCursoDocente.getEvaluacion().getAnio(),
                            evaluacionCursoDocente.getEvaluacion().getPeriodo(),
                            evaluacionCursoDocente.getCursoDocente().getCurso().getId()));
            Double result = evaluacionRespuestaRepository
                    .findAverageValorRespuestaByEvaluacionCursoDocente(evaluacionCursoDocente.getId());
            float nota = result != null ? result.floatValue() : 0;
            estadistica.setNotaPromedio(nota);
            estadisticas.add(estadistica);
        }
        return estadisticas;
    }

    private float calculateGrade(List<EvaluacionEstadisticaCursoDto> estadisticas) {
        // Aquí se calcula la nota final de la asignatura
        // Supongo que la nota final es el promedio de las notas de los docentes
        // Si es así, se puede hacer algo como esto:
        float notaFinal = 0;
        int totalDocentes = estadisticas.size();
        for (EvaluacionEstadisticaCursoDto estadistica : estadisticas) {
            if (estadistica.getTotalRespondidas() > 0) {
                notaFinal += estadistica.getNotaPromedio();
            } else {
                totalDocentes--;
            }
        }
        if (totalDocentes == 0) {
            return 0;

        }
        notaFinal /= totalDocentes;
        return notaFinal;
    }
}
