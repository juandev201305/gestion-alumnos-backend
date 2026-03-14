package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.NotaDto;
import com.juandev201305.app.ejercicio.dtos.NotaFormDto;
import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.repositorys.AlumnoRepository;
import com.juandev201305.app.ejercicio.repositorys.AsignaturaRepository;
import com.juandev201305.app.ejercicio.repositorys.NotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
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
    public List<NotaDto> listarTodo() {
        List<Nota> notas = notaRepo.findAll();
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
    @Transactional
    public NotaDto guardar(NotaFormDto notaForm) {
        Alumno alumnoBd = alumnRepo.findById(notaForm.getIdAlumno())
            .orElseThrow(() -> new RuntimeException("Alumno existente"));

        Asignatura asignaturaBd = asigRepo.findById(notaForm.getIdAsignatura())
            .orElseThrow(() -> new RuntimeException("Asignatura existente"));
        if(notaForm.getNota()>7 || notaForm.getNota()<1){
            throw new RuntimeException("Nota invalida");
        }
        Nota notaBd = new Nota();
        notaBd.setAlumno(alumnoBd);
        notaBd.setAsignatura(asignaturaBd);
        notaBd.setNota(notaForm.getNota());
        notaRepo.save(notaBd);

        NotaDto notaDto = new NotaDto();
        notaDto.setId(notaBd.getId());
        notaDto.setIdAlumno(notaBd.getAlumno().getId());
        notaDto.setNombreAlumno(notaBd.getAlumno().getNombre());
        notaDto.setIdAsignatura(notaBd.getAsignatura().getId());
        notaDto.setAsignatura(notaBd.getAsignatura().getNombre());
        notaDto.setNota(notaBd.getNota());
        return notaDto;
    }

    @Override
    @Transactional
    public NotaDto actualizar(NotaFormDto notaForm, Long idNota) {
        Nota notaBd = notaRepo.findById(idNota)
            .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        Alumno alumnoBd = alumnRepo.findById(notaForm.getIdAlumno())
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Asignatura asignaturaBd = asigRepo.findById(notaForm.getIdAsignatura())
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        if(notaForm.getNota()>7 || notaForm.getNota()<2){
            throw new RuntimeException("Nota invalida");
        }
        notaBd.setAlumno(alumnoBd);
        notaBd.setAsignatura(asignaturaBd);
        notaBd.setNota(notaForm.getNota());
        notaRepo.save(notaBd);

        NotaDto notaDto = new NotaDto();
        notaDto.setId(notaBd.getId());
        notaDto.setIdAlumno(notaBd.getAlumno().getId());
        notaDto.setNombreAlumno(notaBd.getAlumno().getNombre());
        notaDto.setIdAsignatura(notaBd.getAsignatura().getId());
        notaDto.setAsignatura(notaBd.getAsignatura().getNombre());
        notaDto.setNota(notaBd.getNota());
        return notaDto;
    }

    @Override
    @Transactional
    public ApiReponseStatusDto eliminar(Long id) {
        ApiReponseStatusDto dto = new ApiReponseStatusDto();
        Nota notaBd = notaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Nota no encontada"));

        notaRepo.delete(notaBd);
        dto.setSuccess(true);
        dto.setMessage("Nota eliminada!");
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotaDto> notasPorFecha(String fechaInicioStr, String fechaFinalStr) {
        try{
            Instant fechaInicio = Instant.parse(fechaInicioStr);
            Instant fechaFinal = Instant.parse(fechaFinalStr);
            List<Nota> notas = notaRepo.findByFechaBetween(fechaInicio,fechaFinal);
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
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al parsear fechas");
        }

    }


}
