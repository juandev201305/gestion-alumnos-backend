package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.repositorys.AsignaturaRepository;
import com.juandev201305.app.ejercicio.repositorys.NotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * IMPLEMENTACIÓN SERVICE ASIGNATURA:
 * Logica de negocio.
 */
@Service
public class AsignaturaServiceImpl implements AsignaturaService {
    private final AsignaturaRepository asigRepo;
    private final NotaRepository notaRepo;
    public AsignaturaServiceImpl(AsignaturaRepository asig, NotaRepository nota){
        this.asigRepo=asig;
        this.notaRepo = nota;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> listarTodo() {
        return asigRepo.findAll();
    }

    @Override
    @Transactional
    public Asignatura guardar(Asignatura asignatura) {
        return asigRepo.save(asignatura);
    }

    @Override
    @Transactional
    public Asignatura actualizar(Asignatura asignatura) {
        Optional<Asignatura> asignaturaOptional = asigRepo.findById(asignatura.getId());
        if(asignaturaOptional.isEmpty()){
            return null;
        }
        asigRepo.save(asignatura);
        return asignatura;
    }

    @Override
    @Transactional
    public Asignatura eliminar(Long idAsignatura) {
        Optional<Asignatura> optionalAsignatura = asigRepo.findById(idAsignatura);
        if(optionalAsignatura.isEmpty()){
            return null;
        }
        asigRepo.delete(optionalAsignatura.get());
        return optionalAsignatura.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nota> notasPorAsignatura(Long idAsignatura) {
        Optional<Asignatura> optionalAsignatura = asigRepo.findById(idAsignatura);
        if(optionalAsignatura.isEmpty()){
            return null;
        }
        return notaRepo.findByAsignaturaId(idAsignatura);
    }
}
