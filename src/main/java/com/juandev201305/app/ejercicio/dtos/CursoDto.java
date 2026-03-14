package com.juandev201305.app.ejercicio.dtos;

import java.util.List;

public class CursoDto {
    private Long id;
    private Integer nivel;
    private Character letra;
    private String profesorJefe;
    private List<AlumnoDto> alumnos;

    public CursoDto(Long id, Integer nivel, Character letra, String profesorJefe, List<AlumnoDto> alumnos) {
        this.id = id;
        this.nivel = nivel;
        this.letra = letra;
        this.profesorJefe = profesorJefe;
        this.alumnos = alumnos;
    }

    public CursoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getProfesorJefe() {
        return profesorJefe;
    }

    public void setProfesorJefe(String profesorJefe) {
        this.profesorJefe = profesorJefe;
    }

    public List<AlumnoDto> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<AlumnoDto> alumnos) {
        this.alumnos = alumnos;
    }
}
