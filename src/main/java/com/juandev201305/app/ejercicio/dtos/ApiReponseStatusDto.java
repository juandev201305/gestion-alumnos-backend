package com.juandev201305.app.ejercicio.dtos;

/**
 * Clase DTO:
 * Mostrar estado y respuesta a una request que elimine registro en la tabla Alumno
 */
public class ApiReponseStatusDto {
    Boolean success;
    String message;

    public ApiReponseStatusDto() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
