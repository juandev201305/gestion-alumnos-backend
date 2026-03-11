package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.repositorys.AsignaturaRepository;
import com.juandev201305.app.ejercicio.repositorys.NotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Asignatura asignaturaBd = asigRepo.findById(asignatura.getId())
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        asignaturaBd.setNombre(asignatura.getNombre());
        asignaturaBd.setProfesor(asignatura.getProfesor());
        asigRepo.save(asignaturaBd);
        return asignatura;
    }

    @Override
    @Transactional
    public Asignatura eliminar(Long idAsignatura) {
        Asignatura asignaturaBd = asigRepo.findById(idAsignatura)
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        asigRepo.delete(asignaturaBd);
        return asignaturaBd;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nota> notasPorAsignatura(Long idAsignatura) {
        Asignatura asignaturaBd = asigRepo.findById(idAsignatura)
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        return notaRepo.findByAsignaturaId(asignaturaBd.getId());
    }
}
