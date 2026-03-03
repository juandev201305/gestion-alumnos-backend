package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Curso;

import java.util.List;

/**
 * INTERFAZ SERVICE:
 * Tabla Curso
 */
public interface CursoService {
    List<Curso> listarTodo();
    Curso guardar(Curso curso);
    Curso actualizar(Curso curso);
    Curso eliminar(Long idCurso);
    List<Alumno> obtenerAlumnosPorId(Long idCurso);
}
