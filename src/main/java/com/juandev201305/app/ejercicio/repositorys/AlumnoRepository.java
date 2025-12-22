package com.juandev201305.app.ejercicio.repositorys;

import com.juandev201305.app.ejercicio.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno,Long> {
    Optional<Alumno> findByRut(String rut);
    List<Alumno> findByCursoId(Long id);
}
