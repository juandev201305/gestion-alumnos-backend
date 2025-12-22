package com.juandev201305.app.ejercicio.repositorys;

import com.juandev201305.app.ejercicio.models.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota,Long> {
    List<Nota> findByAlumnoId(Long idALumno);
    List<Nota> findByAlumnoIdAndAsignaturaId(Long idALumno, Long idAsignatura);
    List<Nota> findByAsignaturaId(Long idAsignatura);
    List<Nota> findByFechaBetween(Instant initial, Instant end);
    @Query("select n.nota from Nota n where asignatura.id=?1 and alumno.id=?2")
    List<Float> listarNotasDeCadaAsignaturaPorAlumno(Long idAsignatura,Long idAlumno);
}
