package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.AlumnoDto;
import com.juandev201305.app.ejercicio.dtos.AlumnoInformeDto;
import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Curso;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.repositorys.AlumnoRepository;
import com.juandev201305.app.ejercicio.repositorys.CursoRepository;
import com.juandev201305.app.ejercicio.repositorys.NotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * IMPLEMENTACIÓN SERVICE ALUMNO:
 * Logica de negocio.
 */
@Service
public class AlumnoServiceImpl implements AlumnoService {
    private final AlumnoRepository alumRepo;
    private final RutService rutServ;
    private final CursoRepository cursoRepo;
    private final NotaRepository notaRepo;
    private final AsignaturaService asigServ;
    public AlumnoServiceImpl(AlumnoRepository alumn, RutService rut, CursoRepository curso, NotaRepository nota, AsignaturaService asig){
        this.alumRepo=alumn;
        this.rutServ=rut;
        this.cursoRepo=curso;
        this.notaRepo=nota;
        this.asigServ = asig;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listarTodo() {
        return alumRepo.findAll();
    }

    @Override
    @Transactional
    public Alumno guardar(Alumno alumn) {
        String rut = rutServ.verificarRut(alumn.getRut());
        if(rut==null){
            return null;
        }
        alumRepo.findByRut(rut)
            .ifPresent((alumno) -> {
                throw new RuntimeException("Rut existente");
            });
       
        cursoRepo.findById(alumn.getCurso().getId())
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        return alumRepo.save(alumn);
    }

    @Override
    @Transactional
    public Alumno actualizar(Alumno alumn) {
        Alumno alumnoBd = alumRepo.findById(alumn.getId())
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
       
        Curso cursoBd = cursoRepo.findById(alumn.getCurso().getId())
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
     
        rutServ.verificarRut(alumn.getRut());
        alumnoBd.setNombre(alumn.getNombre());
        alumnoBd.setCurso(cursoBd);
        alumnoBd.setRut(alumn.getRut());
        alumRepo.save(alumnoBd);
        return alumn;
    }

    @Override
    @Transactional
    public AlumnoDto eliminar(Long idAlumno) {
        AlumnoDto dto = new AlumnoDto();
        Alumno alumnoBd = alumRepo.findById(idAlumno)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        List<Nota> notasAlumno = notaRepo.findByAlumnoId(idAlumno);
        if(!notasAlumno.isEmpty()){
            dto.setStatus("209");
            dto.setMessage("ALUMNO CON NOTAS EN EL SISTEMA!!");
            return dto;
        }
        alumRepo.delete(alumnoBd);
        dto.setStatus("200");
        dto.setMessage("ALUMNO ELIMINADO CON EXITO!");
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public Curso buscarCursoDelAlumno(Long idALumno) {
        Alumno alumnoBd = alumRepo.findById(idALumno)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
   
        return alumnoBd.getCurso();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nota> notaPorAlumno(Long idAlumno) {
        Alumno alumnoBd = alumRepo.findById(idAlumno)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        return notaRepo.findByAlumnoId(alumnoBd.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Float promedioGeneralAlumno(Long idAlumno) {
        List<Nota> notas = notaRepo.findByAlumnoId(idAlumno);
        if (notas.isEmpty()){
            throw new RuntimeException("Notas no encontrada");
        }
        Float nota=0.0f;
        for(int i = 0;i<notas.size();i++){
            nota+=notas.get(i).getNota();
        }
        return nota/notas.size();
    }

    @Override
    @Transactional(readOnly = true)
    public Float promedioAsignaturaAlumno(Long idAlumno,Long idAsignatura) {
        List<Nota> notas = notaRepo.findByAlumnoIdAndAsignaturaId(idAlumno,idAsignatura);
        if(notas==null){
            return null;
        }
        Float nota=0.0f;
        for(int i = 0;i<notas.size();i++){
            nota+=notas.get(i).getNota();
        }
        return nota/notas.size();
    }


    @Override
    @Transactional(readOnly = true)
    public AlumnoInformeDto informe(Long idAlumno) {
        Alumno alumnoBd = alumRepo.findById(idAlumno)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));


        AlumnoInformeDto informe = new AlumnoInformeDto();
        informe.setNombre(alumnoBd.getNombre());
        informe.setRut(alumnoBd.getRut());
        informe.setNivel(alumnoBd.getCurso().getNivel());
        informe.setLetra(alumnoBd.getCurso().getLetra());
        informe.setAsignaturas(asigServ.listarTodo());

        Map<String,List<Float>> notasAsignatura =new LinkedHashMap<>();
        List<Asignatura> asignaturas = asigServ.listarTodo();

        for(int i = 0;i<asignaturas.size();i++){
            notasAsignatura.put(
                    asignaturas.get(i).getNombre(),
                    notaRepo.listarNotasDeCadaAsignaturaPorAlumno(asignaturas.get(i).getId(),alumnoBd.getId())
            );
        }

        informe.setNotasPorAsignatura(notasAsignatura);

        Map<String,Float> promedioPorAsignatura = new LinkedHashMap<>();

        for(int i = 0 ;i<asignaturas.size();i++){
            Float resultado = 0.0f,promedio = 0.0f;
            List<Float> notas = notaRepo.listarNotasDeCadaAsignaturaPorAlumno(asignaturas.get(i).getId(),alumnoBd.getId());
            resultado = (float) notas.stream().mapToDouble(Float::valueOf).sum();
            try{
                promedio =  resultado/notas.size();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            promedioPorAsignatura.put(asignaturas.get(i).getNombre(), promedio);
        }

        informe.setPromedioPorAsignatura(promedioPorAsignatura);
        informe.setPromedio(promedioGeneralAlumno(alumnoBd.getId()));
        return informe;
    }

    //@Override
    //public List<Float> prueba(Long id,Long id2) {
        //return notaRepo.listarNotasDeCadaAsignaturaPorAlumno(id,id2);
    //}


}

