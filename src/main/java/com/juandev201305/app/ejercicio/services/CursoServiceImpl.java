package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.AlumnoDto;
import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.CursoDto;
import com.juandev201305.app.ejercicio.dtos.CursoFormDto;
import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Curso;
import com.juandev201305.app.ejercicio.repositorys.AlumnoRepository;
import com.juandev201305.app.ejercicio.repositorys.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * IMPLEMENTACIÓN SERVICE CURSO:
 * Logica de negocio.
 */
@Service
public class CursoServiceImpl implements CursoService{
    private final CursoRepository cursoRepo;
    private final AlumnoRepository alumnoRepository;
    public CursoServiceImpl(CursoRepository curso, AlumnoRepository alumnoRepository){
        this.cursoRepo=curso;
        this.alumnoRepository = alumnoRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<CursoDto> listarTodo() {
        List<Curso> cursos = cursoRepo.findAll();
        List<CursoDto> cursoDtos = new ArrayList<>();
        for (Curso curso : cursos) {
            List<AlumnoDto> alumnoDtos = new ArrayList<>();

            for (Alumno alumno : curso.getAlumnos()) {
                alumnoDtos.add(new AlumnoDto(alumno.getId()
                        ,alumno.getNombre()
                        ,alumno.getRut()
                        ,alumno.getCurso().getId()));
            }

            cursoDtos.add(new CursoDto(curso.getId()
                        ,curso.getNivel()
                        ,curso.getLetra()
                        ,curso.getNombreProfesorJefe()
                        ,alumnoDtos));
        }
        return cursoDtos;
    }

    @Override
    @Transactional
    public CursoDto guardar(CursoFormDto cursoForm) {
        cursoRepo.findByLetraAndNivel(cursoForm.getLetra(),cursoForm.getNivel())
            .ifPresent((cursoWithNivelAndLetra) -> {
                throw new RuntimeException("Curso existente");
            });
        
        cursoRepo.findByNombreProfesorJefe(cursoForm.getNombreProfesorJefe())
            .ifPresent((cursoWithProfesorJefe) -> {
                throw new RuntimeException("Curso existente");
            });

        if(cursoForm.getNivel()>4 || cursoForm.getNivel()<1){
            throw new RuntimeException("Error en el nivel de curso ingresado");
        }
        Curso cursoBd = new Curso();
        cursoBd.setNivel(cursoForm.getNivel());
        cursoBd.setLetra(cursoForm.getLetra());
        cursoBd.setNombreProfesorJefe(cursoForm.getNombreProfesorJefe());
        cursoRepo.save(cursoBd);

        List<AlumnoDto> alumnoDtos = new ArrayList<>();
        for (Alumno alumno : cursoBd.getAlumnos()) {
            alumnoDtos.add(new AlumnoDto(alumno.getId()
                    ,alumno.getNombre()
                    ,alumno.getRut()
                    ,alumno.getCurso().getId()));
        }

        CursoDto cursoDto = new CursoDto();
        cursoDto.setId(cursoBd.getId());
        cursoDto.setNivel(cursoBd.getNivel());
        cursoDto.setLetra(cursoBd.getLetra());
        cursoDto.setProfesorJefe(cursoBd.getNombreProfesorJefe());
        cursoDto.setAlumnos(alumnoDtos);
        return cursoDto;
    }

    @Override
    @Transactional
    public CursoDto actualizar(CursoFormDto cursoForm, Long idCurso) {
        Curso cursoBd = cursoRepo.findById(idCurso)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
     

        cursoRepo.findByNombreProfesorJefe(cursoForm.getNombreProfesorJefe())
            .ifPresent((cursoWithProfesorJefe) -> {
                throw new RuntimeException("ProfesorExistente");
            });

        if(cursoForm.getNivel()>4 || cursoForm.getNivel()<1){
            throw new RuntimeException("Nivel del curso erroneo");
        }
        cursoBd.setLetra(cursoForm.getLetra());
        cursoBd.setNivel(cursoForm.getNivel());
        cursoBd.setNombreProfesorJefe(cursoForm.getNombreProfesorJefe());
        cursoRepo.save(cursoBd);

        List<AlumnoDto> alumnoDtos = new ArrayList<>();
        for (Alumno alumno : cursoBd.getAlumnos()) {
            alumnoDtos.add(new AlumnoDto(alumno.getId()
                    ,alumno.getNombre()
                    ,alumno.getRut()
                    ,alumno.getCurso().getId()));
        }

        CursoDto cursoDto = new CursoDto();
        cursoDto.setId(cursoBd.getId());
        cursoDto.setNivel(cursoBd.getNivel());
        cursoDto.setLetra(cursoBd.getLetra());
        cursoDto.setProfesorJefe(cursoBd.getNombreProfesorJefe());
        cursoDto.setAlumnos(alumnoDtos);
        return cursoDto;
    }

    @Override
    @Transactional
    public ApiReponseStatusDto eliminar(Long idCurso) {
        ApiReponseStatusDto dto = new ApiReponseStatusDto();
        Curso cursoBd = cursoRepo.findById(idCurso)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        cursoRepo.delete(cursoBd);
        dto.setSuccess(true);
        dto.setMessage("Curso eliminado!");
        return dto;
    }

    @Override
    @Transactional
    public List<AlumnoDto> obtenerAlumnosPorId(Long idCurso) {
        Curso cursoBd = cursoRepo.findById(idCurso)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        List<Alumno> alumnos = alumnoRepository.findByCursoId(idCurso);
        List<AlumnoDto> alumnoDtos = new ArrayList<>();
        for(Alumno alumno: alumnos) {
            alumnoDtos.add(new AlumnoDto(alumno.getId(),alumno.getNombre(),alumno.getRut(),cursoBd.getId()));
        }
        return alumnoDtos;
    }
}
