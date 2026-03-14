package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.AsignaturaDto;
import com.juandev201305.app.ejercicio.dtos.AsignaturaFormDto;
import com.juandev201305.app.ejercicio.dtos.NotaDto;
import java.util.List;

/**
 * INTERFAZ SERVICE:
 * Tabla Asignatura
 */
public interface AsignaturaService {
    List<AsignaturaDto> listarTodo();
    AsignaturaDto guardar(AsignaturaFormDto asignatura);
    AsignaturaDto actualizar(AsignaturaFormDto asignatura,Long idAsignatura);
    ApiReponseStatusDto eliminar(Long idAsignatura);
    List<NotaDto> notasPorAsignatura(Long idAsignatura);
}
