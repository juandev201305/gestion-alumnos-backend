package com.juandev201305.app.ejercicio.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import java.util.List;
import java.util.Map;

/**
 * Clase AlumnoInformeDto:
 * Mostrar un informe completo de un alumno
 */
@JsonPropertyOrder({ "nombre",
        "rut",
        "nivel",
        "letra",
        "asignaturas",
        "notasPorAsignatura",
        "promedioPorAsignatura",
        "promedio"
})
public class AlumnoInformeDto {
    private String nombre;
    private String rut;
    private Integer nivel;
    private Character letra;
    private List<AsignaturaDto> asignaturas;
    private Map<String, List<Float>> notasPorAsignatura;
    private Map<String,Float> promedioPorAsignatura;
    private Float promedio;

    public AlumnoInformeDto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Character getLetra() {
        return letra;
    }

    public void setLetra(Character letra) {
        this.letra = letra;
    }

    public List<AsignaturaDto> getAsignaturasDto() {
        return asignaturas;
    }

    public void setAsignaturas(List<AsignaturaDto> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public Map<String, List<Float>> getNotasPorAsignatura() {
        return notasPorAsignatura;
    }

    public void setNotasPorAsignatura(Map<String, List<Float>> notasPorAsignatura) {
        this.notasPorAsignatura = notasPorAsignatura;
    }

    public Map<String, Float> getPromedioPorAsignatura() {
        return promedioPorAsignatura;
    }

    public void setPromedioPorAsignatura(Map<String, Float> promedioPorAsignatura) {
        this.promedioPorAsignatura = promedioPorAsignatura;
    }

    public Float getPromedio() {
        return promedio;
    }

    public void setPromedio(Float promedio) {
        this.promedio = promedio;
    }
}
