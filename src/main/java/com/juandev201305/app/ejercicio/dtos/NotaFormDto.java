package com.juandev201305.app.ejercicio.dtos;

public class NotaFormDto {
    private Long idAlumno;
    private Long idAsignatura;
    private Float nota;

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Long getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(Long idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    @Override
    public String toString(){
        return "HOla"+idAlumno+"x"+idAsignatura+"algo"+nota;
    }
}
