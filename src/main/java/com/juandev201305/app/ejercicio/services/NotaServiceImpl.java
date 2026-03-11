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
import java.util.Optional;

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
        Optional<Alumno> alumnoOpt = alumnRepo.findById(nota.getAlumno().getId());
        if(alumnoOpt.isEmpty()){
            return null;
        }
        Optional<Asignatura> asignatura = asigRepo.findById(nota.getAsignatura().getId());
        if(asignatura.isEmpty()){
            return null;
        }
        return notaRepo.save(nota);
    }

    @Override
    @Transactional
    public Nota actualizar(Nota no) {
        Optional<Nota> optionalNota = notaRepo.findById(no.getId());
        if(optionalNota.isEmpty()){
            return null;
        }
        Optional<Alumno> alumnoOpt = alumnRepo.findById(no.getAlumno().getId());
        if(alumnoOpt.isEmpty()){
            return null;
        }
        Optional<Asignatura> asignatura = asigRepo.findById(no.getAsignatura().getId());
        if(asignatura.isEmpty()){
            return null;
        }
        if(no.getNota()>7 || no.getNota()<2){
            return null;
        }
        notaRepo.save(no);
        return no;
    }

    @Override
    @Transactional
    public Nota eliminar(Long id) {
        Optional<Nota> optionalNota = notaRepo.findById(id);
        if(optionalNota.isEmpty()) {
            return null;
        }
        notaRepo.delete(optionalNota.get());
        return optionalNota.get();
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
