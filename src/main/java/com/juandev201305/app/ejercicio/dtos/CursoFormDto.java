package com.juandev201305.app.ejercicio.dtos;

public class CursoFormDto {
    private Integer nivel;
    private Character letra;
    private String nombreProfesorJefe;

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

    public String getNombreProfesorJefe() {
        return nombreProfesorJefe;
    }

    public void setNombreProfesorJefe(String nombreProfesorJefe) {
        this.nombreProfesorJefe = nombreProfesorJefe;
    }
}
