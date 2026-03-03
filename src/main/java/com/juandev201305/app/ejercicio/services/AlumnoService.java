package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.AlumnoDto;
import com.juandev201305.app.ejercicio.dtos.AlumnoInformeDto;
import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Curso;
import com.juandev201305.app.ejercicio.models.Nota;

import java.util.List;

/**
 * INTERFAZ SERVICE:
 * Tabla Alumno
 */
public interface AlumnoService {
    List<Alumno> listarTodo();
    Alumno guardar(Alumno alumn);
    Alumno actualizar(Alumno alumn);
    AlumnoDto eliminar(Long idAlumno);
    Curso buscarCursoDelAlumno(Long idAlumno);
    List<Nota> notaPorAlumno(Long idAlumno);
    Float promedioGeneralAlumno(Long idAlumno);
    Float promedioAsignaturaAlumno(Long idAlumno,Long idAsignatura);
    AlumnoInformeDto informe (Long idAlumno);

    List<Float> prueba (Long id,Long id2);
}
