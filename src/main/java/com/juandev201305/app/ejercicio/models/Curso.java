package com.juandev201305.app.ejercicio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.util.List;
/**
 * CLASE POJO:
 * Modelamiento de la tabla Curso
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//Ordenar estructura del JSON
@JsonPropertyOrder({
        "id",
        "nivel",
        "letra",
        "nombreProfesorJefe",
        "alumnos"
})
public class Curso {
    //Declaración de columnas como atributos en la Clase

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Character letra;

    @Column(nullable = false,name = "nombre_profesor_jefe")
    private String nombreProfesorJefe;

    @OneToMany(mappedBy = "curso")
    //La anotación indica la serialización de la Clase Hija
    @JsonManagedReference //Aplicar en la tabla padre
    List<Alumno> alumnos;

    //Constructor vacio (Requerido por JPA/Hibernate)
    public Curso() {
    }

    //Metodos setters y getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getLetra() {
        return letra;
    }

    public void setLetra(Character letra) {
        this.letra = letra;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getNombreProfesorJefe() {
        return nombreProfesorJefe;
    }

    public void setNombreProfesorJefe(String nombreProfesorJefe) {
        this.nombreProfesorJefe = nombreProfesorJefe;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
