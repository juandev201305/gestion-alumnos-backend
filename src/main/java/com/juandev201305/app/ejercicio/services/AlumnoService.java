package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.*;
import com.juandev201305.app.ejercicio.models.Curso;

import java.util.List;

/**
 * INTERFAZ SERVICE:
 * Tabla Alumno
 */
public interface AlumnoService {
    List<AlumnoDto> listarTodo();
    AlumnoDto guardar(AlumnoFormDto alumn);
    AlumnoDto actualizar(AlumnoFormDto alumn, Long idAlumno);
    ApiReponseStatusDto eliminar(Long idAlumno);
    Curso buscarCursoDelAlumno(Long idAlumno);
    List<NotaDto> notaPorAlumno(Long idAlumno);
    Float promedioGeneralAlumno(Long idAlumno);
    Float promedioAsignaturaAlumno(Long idAlumno,Long idAsignatura);
    AlumnoInformeDto informe (Long idAlumno);

    //List<Float> prueba (Long id,Long id2);
}
