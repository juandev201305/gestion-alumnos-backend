package com.juandev201305.app.ejercicio.repositorys;

import com.juandev201305.app.ejercicio.models.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * INTERFAZ REPOSITORIO:
 * Consultas SQL de la tabla Asignatura
 */
public interface AsignaturaRepository extends JpaRepository<Asignatura,Long> {
}
