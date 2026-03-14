package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.AlumnoDto;
import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.CursoDto;
import com.juandev201305.app.ejercicio.dtos.CursoFormDto;

import java.util.List;

/**
 * INTERFAZ SERVICE:
 * Tabla Curso
 */
public interface CursoService {
    List<CursoDto> listarTodo();
    CursoDto guardar(CursoFormDto curso);
    CursoDto actualizar(CursoFormDto curso, Long idCurso);
    ApiReponseStatusDto eliminar(Long idCurso);
    List<AlumnoDto> obtenerAlumnosPorId(Long idCurso);
}
