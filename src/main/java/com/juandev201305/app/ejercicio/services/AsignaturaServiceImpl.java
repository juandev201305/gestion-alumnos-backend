package com.juandev201305.app.ejercicio.services;

import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.AsignaturaDto;
import com.juandev201305.app.ejercicio.dtos.AsignaturaFormDto;
import com.juandev201305.app.ejercicio.dtos.NotaDto;
import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.repositorys.AsignaturaRepository;
import com.juandev201305.app.ejercicio.repositorys.NotaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<AsignaturaDto> listarTodo() {
        List<Asignatura> asignaturas = asigRepo.findAll();
        List<AsignaturaDto> asignaturaDtos = new ArrayList<>();
        for(Asignatura asignatura: asignaturas) {
            asignaturaDtos.add(new AsignaturaDto(asignatura.getId(),asignatura.getNombre(),asignatura.getProfesor()));
        }
        return asignaturaDtos;
    }

    @Override
    @Transactional
    public AsignaturaDto guardar(AsignaturaFormDto asignaturaForm) {
        Asignatura asignaturaBd = new Asignatura();
        asignaturaBd.setNombre(asignaturaForm.getNombre());
        asignaturaBd.setProfesor(asignaturaForm.getProfesor());
        asigRepo.save(asignaturaBd);

        AsignaturaDto asignaturaDto = new AsignaturaDto();
        asignaturaDto.setId(asignaturaBd.getId());
        asignaturaDto.setNombre(asignaturaBd.getNombre());
        asignaturaDto.setProfesor(asignaturaBd.getProfesor());
        return asignaturaDto;
    }

    @Override
    @Transactional
    public AsignaturaDto actualizar(AsignaturaFormDto asignaturaForm, Long idAsignatura) {
        Asignatura asignaturaBd = asigRepo.findById(idAsignatura)
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        asignaturaBd.setNombre(asignaturaForm.getNombre());
        asignaturaBd.setProfesor(asignaturaForm.getProfesor());
        asigRepo.save(asignaturaBd);
        AsignaturaDto asignaturaDto = new AsignaturaDto();
        asignaturaDto.setId(asignaturaBd.getId());
        asignaturaDto.setNombre(asignaturaBd.getNombre());
        asignaturaDto.setProfesor(asignaturaBd.getProfesor());
        return asignaturaDto;
    }

    @Override
    @Transactional
    public ApiReponseStatusDto eliminar(Long idAsignatura) {
        ApiReponseStatusDto dto = new ApiReponseStatusDto();
        Asignatura asignaturaBd = asigRepo.findById(idAsignatura)
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        asigRepo.delete(asignaturaBd);
        dto.setSuccess(true);
        dto.setMessage("Asignatura eliminada!");
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotaDto> notasPorAsignatura(Long idAsignatura) {
        Asignatura asignaturaBd = asigRepo.findById(idAsignatura)
            .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        List<Nota> notas = notaRepo.findByAsignaturaId(asignaturaBd.getId());
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
}
