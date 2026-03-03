package com.juandev201305.app.ejercicio.dtos;

/**
 * Clase DTO:
 * Mostrar estado y respuesta a una request que elimine registro en la tabla Alumno
 */
public class AlumnoDto {
    String status;
    String message;

    public AlumnoDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
