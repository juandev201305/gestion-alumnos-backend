package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Curso;
import com.juandev201305.app.ejercicio.repositorys.AlumnoRepository;
import com.juandev201305.app.ejercicio.repositorys.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<Curso> optionalLetraOrNivel = cursoRepo.findByLetraAndNivel(curso.getLetra(),curso.getNivel());
        if(optionalLetraOrNivel.isPresent()){
            return null;
        }
        Optional<Curso> optionalProfesorJefe = cursoRepo.findByNombreProfesorJefe(curso.getNombreProfesorJefe());
        if(optionalProfesorJefe.isPresent()){
            return null;
        }
        if(curso.getNivel()>4 || curso.getNivel()<1){
            return null;
        }
        return cursoRepo.save(curso);
    }

    @Override
    @Transactional
    public Curso actualizar(Curso curso) {
        Optional<Curso> optionalCurso = cursoRepo.findById(curso.getId());
        if(optionalCurso.isEmpty()){
            return null;
        }

        Optional<Curso> optionalProfesorJefe = cursoRepo.findByNombreProfesorJefe(curso.getNombreProfesorJefe());
        if(optionalProfesorJefe.isPresent()){
            return null;
        }

        if(curso.getNivel()>4 && curso.getNivel()<1){
            return null;
        }
        curso.setLetra(curso.getLetra());
        cursoRepo.save(curso);
        return curso;
    }

    @Override
    @Transactional
    public Curso eliminar(Long idCurso) {
        Optional<Curso> cursoOptional = cursoRepo.findById(idCurso);
        if(cursoOptional.isEmpty()){
            return null;
        }
        cursoRepo.delete(cursoOptional.get());
        return cursoOptional.get();
    }

    @Override
    @Transactional
    public List<Alumno> obtenerAlumnosPorId(Long idCurso) {
        Optional<Curso> optionalCurso = cursoRepo.findById(idCurso);
        if(optionalCurso.isEmpty()){
            return null;
        }
        return optionalCurso.get().getAlumnos();
    }
}
