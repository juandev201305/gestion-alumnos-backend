package com.juandev201305.app.ejercicio.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

/**
 * CLASE POJO:
 * Modelamiento de la tabla Alumno
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//Ordenar estructura del JSON
@JsonPropertyOrder({
        "id",
        "nombre",
        "rut",
        "curso"})
public class Alumno {
    //Declaración de columnas como atributos en la Clase
    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false,length = 9,unique = true)
    private String rut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    //La anotación indica la no serialización de la Clase Padre
    @JsonBackReference //Aplicar en la tabla hija
    private Curso curso;

    //Constructor vacio (Requerido por JPA/Hibernate)
    public Alumno() {
    }

    //Metodos setters y getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
