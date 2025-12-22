package com.juandev201305.app.ejercicio.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.juandev201305.app.ejercicio.dtos.AlumnoDto;
import com.juandev201305.app.ejercicio.dtos.AlumnoInformeDto;
import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Curso;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.repositorys.AlumnoRepository;
import com.juandev201305.app.ejercicio.repositorys.AsignaturaRepository;
import com.juandev201305.app.ejercicio.repositorys.CursoRepository;
import com.juandev201305.app.ejercicio.repositorys.NotaRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

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
        Optional<Alumno> alumnoOpt = alumRepo.findByRut(rut);
        if(alumnoOpt.isPresent()){
            return null;
        }
        Optional<Curso> cursoOptional = cursoRepo.findById(alumn.getCurso().getId());
        if(cursoOptional.isEmpty()){
            return null;
        }
        return alumRepo.save(alumn);
    }

    @Override
    @Transactional
    public Alumno actualizar(Alumno alumn) {
        Optional<Alumno> optionalAlumno = alumRepo.findById(alumn.getId());
        if(optionalAlumno.isEmpty()){
            return null;
        }
        Optional<Curso> cursoOptional = cursoRepo.findById(alumn.getCurso().getId());
        if(cursoOptional.isEmpty()){
            return null;
        }
        rutServ.verificarRut(alumn.getRut());
        alumRepo.save(optionalAlumno.get());
        return alumn;
    }

    @Override
    @Transactional
    public AlumnoDto eliminar(Long idAlumno) {
        AlumnoDto dto = new AlumnoDto();
        Optional<Alumno> optionalAlumno = alumRepo.findById(idAlumno);
        if(optionalAlumno.isEmpty()){
            return null;
        }
        List<Nota> notasAlumno = notaRepo.findByAlumnoId(idAlumno);
        if(!notasAlumno.isEmpty()){
            dto.setStatus("209");
            dto.setMessage("ALUMNO CON NOTAS EN EL SISTEMA!!");
            return dto;
        }
        alumRepo.delete(optionalAlumno.get());
        dto.setStatus("200");
        dto.setMessage("ALUMNO ELIMINADO CON EXITO!");
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public Curso buscarCursoDelAlumno(Long idALumno) {
        Optional<Alumno> optionalAlumno = alumRepo.findById(idALumno);
        if(optionalAlumno.isEmpty()){
            return null;
        }
        return optionalAlumno.get().getCurso();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nota> notaPorAlumno(Long idAlumno) {
        Optional<Alumno> optionalAlumno = alumRepo.findById(idAlumno);
        if(optionalAlumno.isEmpty()){
            return null;
        }
        return notaRepo.findByAlumnoId(idAlumno);
    }

    @Override
    public Float promedioGeneralAlumno(Long idAlumno) {
        List<Nota> notas = notaRepo.findByAlumnoId(idAlumno);
        if (notas==null){
            return null;
        }
        Float nota=0.0f;
        for(int i = 0;i<notas.size();i++){
            nota+=notas.get(i).getNota();
        }
        return nota/notas.size();
    }

    @Override
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
    public AlumnoInformeDto informe(Long idAlumno) {
        Optional<Alumno> optionalAlumno = alumRepo.findById(idAlumno);
        if(optionalAlumno.isEmpty()){
            return null;
        }
        Alumno alumno = optionalAlumno.get();
        AlumnoInformeDto informe = new AlumnoInformeDto();
        informe.setNombre(alumno.getNombre());
        informe.setRut(alumno.getRut());
        informe.setNivel(alumno.getCurso().getNivel());
        informe.setLetra(alumno.getCurso().getLetra());
        informe.setAsignaturas(asigServ.listarTodo());
        Map<String,List<Float>> notasAsignatura =new LinkedHashMap<>();
        List<Asignatura> asignaturas = asigServ.listarTodo();
        for(int i = 0;i<asignaturas.size();i++){
            notasAsignatura.put(
                    asignaturas.get(i).getNombre(),
                    notaRepo.listarNotasDeCadaAsignaturaPorAlumno(asignaturas.get(i).getId(),alumno.getId())
            );
        }
        informe.setNotasPorAsignatura(notasAsignatura);
        Map<String,Float> promedioPorAsignatura = new LinkedHashMap<>();
        DecimalFormat formato = new DecimalFormat("#.00");
        for(int i = 0 ;i<asignaturas.size();i++){
            Float resultado = 0.0f,promedio = 0.0f;
            List<Float> notas = notaRepo.listarNotasDeCadaAsignaturaPorAlumno(asignaturas.get(i).getId(),alumno.getId());
            resultado = (float) notas.stream().mapToDouble(Float::valueOf).sum();
            try{
                promedio =  resultado/notas.size();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            promedioPorAsignatura.put(asignaturas.get(i).getNombre(), promedio);
        }
        informe.setPromedioPorAsignatura(promedioPorAsignatura);
        informe.setPromedio(promedioGeneralAlumno(alumno.getId()));
        return informe;
    }

    @Override
    public List<Float> prueba(Long id,Long id2) {
        return notaRepo.listarNotasDeCadaAsignaturaPorAlumno(id,id2);
    }


}

