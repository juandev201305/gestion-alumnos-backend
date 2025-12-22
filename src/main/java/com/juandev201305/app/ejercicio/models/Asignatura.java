package com.juandev201305.app.ejercicio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
/**
 * CLASE POJO:
 * Modelamiento de la tabla Asignatura
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//Ordenar estructura del JSON
@JsonPropertyOrder({
        "id",
        "nombre",
        "profesor"
})
public class Asignatura {
    //Declaración de columnas como atributos en la Clase

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String profesor;

    //Constructor vacio (Requerido por JPA/Hibernate)
    public Asignatura() {
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

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
}
