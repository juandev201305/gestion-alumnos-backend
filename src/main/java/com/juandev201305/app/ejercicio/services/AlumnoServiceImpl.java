package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.*;
import com.juandev201305.app.ejercicio.models.Alumno;
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
    public List<AlumnoDto> listarTodo() {
        List<AlumnoDto> alumnoDtos = new ArrayList<>();
        List<Alumno> alumnos = alumRepo.findAll();
        for(Alumno alumno: alumnos) {
            alumnoDtos.add(new AlumnoDto(alumno.getId(),alumno.getNombre(),alumno.getRut(),alumno.getCurso().getId()));
        }
        return alumnoDtos;
    }

    @Override
    @Transactional
    public AlumnoDto guardar(AlumnoFormDto alumnoForm) {
        String rut = rutServ.verificarRut(alumnoForm.getRut());
        if(rut==null){
            return null;
        }
        alumRepo.findByRut(rut)
            .ifPresent((alumno) -> {
                throw new RuntimeException("Rut existente");
            });
       
        Curso curso = cursoRepo.findById(alumnoForm.getIdCurso())
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        Alumno alumnoBd = new Alumno();
        alumnoBd.setNombre(alumnoForm.getNombre());
        alumnoBd.setRut(alumnoForm.getRut());
        alumnoBd.setCurso(curso);
        alumRepo.save(alumnoBd);

        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setId(alumnoBd.getId());
        alumnoDto.setNombre(alumnoBd.getNombre());
        alumnoDto.setRut(alumnoBd.getRut());
        alumnoDto.setIdCurso(alumnoBd.getCurso().getId());
        return alumnoDto;
    }

    @Override
    @Transactional
    public AlumnoDto actualizar(AlumnoFormDto alumnoForm, Long idAlumno) {
        Alumno alumnoBd = alumRepo.findById(idAlumno)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
       
        Curso cursoBd = cursoRepo.findById(alumnoForm.getIdCurso())
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
     
        rutServ.verificarRut(alumnoForm.getRut());
        alumnoBd.setNombre(alumnoForm.getNombre());
        alumnoBd.setCurso(cursoBd);
        alumnoBd.setRut(alumnoForm.getRut());
        alumRepo.save(alumnoBd);

        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setId(alumnoBd.getId());
        alumnoDto.setNombre(alumnoBd.getNombre());
        alumnoDto.setRut(alumnoBd.getRut());
        alumnoDto.setIdCurso(alumnoBd.getCurso().getId());
        return alumnoDto;
    }

    @Override
    @Transactional
    public ApiReponseStatusDto eliminar(Long idAlumno) {
        ApiReponseStatusDto dto = new ApiReponseStatusDto();
        Alumno alumnoBd = alumRepo.findById(idAlumno)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        List<Nota> notasAlumno = notaRepo.findByAlumnoId(idAlumno);
        if(!notasAlumno.isEmpty()){
            throw new RuntimeException("Alumno con notas en el sistema!!");
        }
        alumRepo.delete(alumnoBd);
        dto.setSuccess(true);
        dto.setMessage("Alumno eliminado!");
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
    public List<NotaDto> notaPorAlumno(Long idAlumno) {
        Alumno alumnoBd = alumRepo.findById(idAlumno)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        List<Nota> notas = notaRepo.findByAlumnoId(alumnoBd.getId());
        List<NotaDto> notaDtos = new ArrayList<>();
        for(Nota nota: notas) {
            notaDtos.add(new NotaDto(nota.getId()
                    ,nota.getAlumno().getId()
                    ,nota.getAlumno().getNombre()
                    ,nota.getAsignatura().getId()
                    ,nota.getAsignatura().getNombre()
                    ,nota.getNota()));
        }
        return notaDtos;
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
            throw new RuntimeException("El alumno no tiene notas en la asignatura");
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
        List<AsignaturaDto> asignaturas = asigServ.listarTodo();

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
}

