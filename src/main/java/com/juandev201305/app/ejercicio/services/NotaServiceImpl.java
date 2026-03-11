package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.repositorys.AlumnoRepository;
import com.juandev201305.app.ejercicio.repositorys.AsignaturaRepository;
import com.juandev201305.app.ejercicio.repositorys.NotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * IMPLEMENTACIÓN SERVICE NOTA:
 * Logica de negocio.
 */
@Service
public class NotaServiceImpl implements NotaService {
    private final NotaRepository notaRepo;
    private final AlumnoRepository alumnRepo;
    private final AsignaturaRepository asigRepo;

    public NotaServiceImpl(NotaRepository nota,AlumnoRepository alumn,AsignaturaRepository asig){
        this.notaRepo=nota;
        this.alumnRepo=alumn;
        this.asigRepo=asig;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Nota> listarTodo() {
        return notaRepo.findAll();
    }

    @Override
    @Transactional
    public Nota guardar(Nota nota) {
        alumnRepo.findById(nota.getAlumno().getId())
            .ifPresent((alumno) -> {
                throw new RuntimeException("Alumno existente");
            });
        asigRepo.findById(nota.getAsignatura().getId())
            .ifPresent((asignatura) -> {
                throw new RuntimeException("Asignatura existente");
            });;
        return notaRepo.save(nota);
    }

    @Override
    @Transactional
    public Nota actualizar(Nota nota) {
        Nota notaBd = notaRepo.findById(nota.getId())
            .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        Alumno alumnoBd = alumnRepo.findById(nota.getAlumno().getId())
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Asignatura asignaturaBd = asigRepo.findById(nota.getAsignatura().getId())
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        if(nota.getNota()>7 || nota.getNota()<2){
            return null;
        }
        notaBd.setAlumno(alumnoBd);
        notaBd.setAsignatura(asignaturaBd);
        notaBd.setNota(nota.getNota());
        notaRepo.save(notaBd);
        return nota;
    }

    @Override
    @Transactional
    public Nota eliminar(Long id) {
        Nota notaBd = notaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Nota no encontada"));

        notaRepo.delete(notaBd);
        return notaBd;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nota> notasPorFecha(String fechaInicioStr, String fechaFinalStr) {
        try{
            Instant fechaInicio = Instant.parse(fechaInicioStr);
            Instant fechaFinal = Instant.parse(fechaFinalStr);
            return notaRepo.findByFechaBetween(fechaInicio,fechaFinal);
        } catch (Exception e) {
            return null;
        }

    }


}
