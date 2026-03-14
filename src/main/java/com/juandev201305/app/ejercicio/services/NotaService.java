package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.NotaDto;
import com.juandev201305.app.ejercicio.dtos.NotaFormDto;

import java.util.List;

/**
 * INTERFAZ SERVICE:
 * Tabla Nota
 */
public interface NotaService {
    List<NotaDto> listarTodo();
    NotaDto guardar(NotaFormDto no);
    NotaDto actualizar(NotaFormDto no, Long idNota);
    ApiReponseStatusDto eliminar(Long id);
    List<NotaDto> notasPorFecha(String fechaInicioStr,String fechaFinalStr);
}
