package com.juandev201305.app.ejercicio.dtos;

public class NotaDto {
    private Long id;
    private Long idAlumno;
    private String nombreAlumno;
    private Long idAsignatura;
    private String asignatura;
    private Float nota;

    public NotaDto(Long id, Long idAlumno, String nombreAlumno, Long idAsignatura, String asignatura, Float nota) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.idAsignatura = idAsignatura;
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public NotaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public Long getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Long idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }
}
