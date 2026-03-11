package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Curso;
import com.juandev201305.app.ejercicio.repositorys.AlumnoRepository;
import com.juandev201305.app.ejercicio.repositorys.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * IMPLEMENTACIÓN SERVICE CURSO:
 * Logica de negocio.
 */
@Service
public class CursoServiceImpl implements CursoService{
    private final CursoRepository cursoRepo;
    public CursoServiceImpl(CursoRepository curso,AlumnoRepository alumno){
        this.cursoRepo=curso;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarTodo() {
        return cursoRepo.findAll();
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        cursoRepo.findByLetraAndNivel(curso.getLetra(),curso.getNivel())
            .ifPresent((cursoWithNivelAndLetra) -> {
                throw new RuntimeException("Curso existente");
            });
        
        cursoRepo.findByNombreProfesorJefe(curso.getNombreProfesorJefe())
            .ifPresent((cursoWithProfesorJefe) -> {
                throw new RuntimeException("Curso existente");
            });

        if(curso.getNivel()>4 || curso.getNivel()<1){
            return null;
        }
        return cursoRepo.save(curso);
    }

    @Override
    @Transactional
    public Curso actualizar(Curso curso) {
        Curso cursoBd = cursoRepo.findById(curso.getId())
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
     

        cursoRepo.findByNombreProfesorJefe(curso.getNombreProfesorJefe())
            .ifPresent((cursoWithProfesorJefe) -> {
                throw new RuntimeException("ProfesorExistente");
            });

        if(curso.getNivel()>4 && curso.getNivel()<1){
            throw new RuntimeException("Nivel del curso erroneo");
        }
        cursoBd.setLetra(curso.getLetra());
        cursoBd.setNivel(curso.getNivel());
        cursoBd.setNombreProfesorJefe(curso.getNombreProfesorJefe());
        cursoRepo.save(cursoBd);
        return curso;
    }

    @Override
    @Transactional
    public Curso eliminar(Long idCurso) {
        Curso cursoBd = cursoRepo.findById(idCurso)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        cursoRepo.delete(cursoBd);
        return cursoBd;
    }

    @Override
    @Transactional
    public List<Alumno> obtenerAlumnosPorId(Long idCurso) {
        Curso cursoBd = cursoRepo.findById(idCurso)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        return cursoBd.getAlumnos();
    }
}
