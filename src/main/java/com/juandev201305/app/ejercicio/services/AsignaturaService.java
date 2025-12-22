package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Nota;

import java.util.List;

public interface AsignaturaService {
    List<Asignatura> listarTodo();
    Asignatura guardar(Asignatura asignatura);
    Asignatura actualizar(Asignatura asignatura);
    Asignatura eliminar(Long idAsignatura);
    List<Nota> notasPorAsignatura(Long idAsignatura);
}
