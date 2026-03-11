package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.models.Nota;

import java.util.List;

/**
 * INTERFAZ SERVICE:
 * Tabla Nota
 */
public interface NotaService {
    List<Nota> listarTodo();
    Nota guardar(Nota no);
    Nota actualizar(Nota no);
    Nota eliminar(Long id);
    List<Nota> notasPorFecha(String fechaInicioStr,String fechaFinalStr);
}
