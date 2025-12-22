package com.juandev201305.app.ejercicio.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.Instant;

/**
 * CLASE POJO:
 * Modelamiento de la tabla Nota
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//Ordenar estructura del JSON
@JsonPropertyOrder({
        "id",
        "alumno",
        "asignatura",
        "nota",
        "fecha"
})
public class Nota {
    //Declaración de columnas como atributos en la Clase

    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_alumno")
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_asignatura")
    private Asignatura asignatura;

    @Column(nullable = false)
    private Float nota;

    @Column(nullable = false)
    private Instant fecha;

    //Constructor vacio (Requerido por JPA/Hibernate)
    public Nota() {
    }
    //Este metodo se ejecuta antes persistir el objeto en la BD
    @PrePersist
    public void dateActual() {
        //Al atributo fecha se le asigna la fecha actual
        this.fecha = Instant.now();
    }

    //Metodos setters y getters
    public Instant getFecha() {
        return fecha;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
