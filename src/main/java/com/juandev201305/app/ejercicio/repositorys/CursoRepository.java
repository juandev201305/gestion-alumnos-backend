package com.juandev201305.app.ejercicio.repositorys;

import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * INTERFAZ REPOSITORIO:
 * Consultas SQL de la tabla Curso
 */
public interface CursoRepository extends JpaRepository<Curso,Long> {
    Optional<Curso> findByLetraAndNivel(Character letra, Integer nivel);
    Optional<Curso> findByNombreProfesorJefe(String nombre);
}
