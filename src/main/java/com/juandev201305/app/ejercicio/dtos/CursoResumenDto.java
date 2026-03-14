package com.juandev201305.app.ejercicio.dtos;

public class CursoResumenDto {
    private Long id;
    private Integer nivel;
    private Character letra;
    private String profesorJefe;

    public CursoResumenDto() {
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
}
