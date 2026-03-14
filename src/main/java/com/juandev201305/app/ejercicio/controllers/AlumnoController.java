package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.dtos.*;
import com.juandev201305.app.ejercicio.models.Curso;
import com.juandev201305.app.ejercicio.services.AlumnoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase RestController:
 * Tabla Alumno
 */
@RestController
@RequestMapping("/alumno")
@CrossOrigin(origins = "*")
public class AlumnoController {
    private final AlumnoService alumServ;
    public AlumnoController(AlumnoService alumn){
        this.alumServ=alumn;
    }
    @GetMapping
    public ResponseEntity<List<AlumnoDto>> listartodo(){
        return ResponseEntity.ok(alumServ.listarTodo());
    }

    @PostMapping
    public ResponseEntity<AlumnoDto> crear(@RequestBody AlumnoFormDto alumnoForm){
        AlumnoDto alumnoDto = alumServ.guardar(alumnoForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumnoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDto> actualizar(@RequestBody AlumnoFormDto alumnoForm, @PathVariable Long id){
        AlumnoDto alumnoDto = alumServ.actualizar(alumnoForm,id);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumnoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiReponseStatusDto> eliminar(@PathVariable Long id){
        ApiReponseStatusDto response = alumServ.eliminar(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{idAlumno}/curso")
    public ResponseEntity<CursoResumenDto> cursoDeAlumno(@PathVariable Long idAlumno){
        Curso curso = alumServ.buscarCursoDelAlumno(idAlumno);
        CursoResumenDto cursoDto = new CursoResumenDto();
        cursoDto.setId(curso.getId());
        cursoDto.setNivel(curso.getNivel());
        cursoDto.setLetra(curso.getLetra());
        cursoDto.setProfesorJefe(curso.getNombreProfesorJefe());
        return ResponseEntity.ok(cursoDto);
    }

    @GetMapping("/{idAlumno}/notas")
    public ResponseEntity<List<NotaDto>> notasPorAlumnos(@PathVariable Long idAlumno){
        List<NotaDto> notas = alumServ.notaPorAlumno(idAlumno);
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/{idAlumno}/promedio")
    public ResponseEntity<Float> promedioGeneral(@PathVariable Long idAlumno){
        Float promedio = alumServ.promedioGeneralAlumno(idAlumno);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promedio);
    }

    @GetMapping("/{idAlumno}/promedio/asignatura/{idAsignatura}")
    public ResponseEntity<Float> promedioAsignatura(@PathVariable Long idAlumno, @PathVariable Long idAsignatura){
        Float promedio = alumServ.promedioAsignaturaAlumno(idAlumno,idAsignatura);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promedio);
    }

    @GetMapping("/{idAlumno}/informe")
    public ResponseEntity<AlumnoInformeDto> informe (@PathVariable Long idAlumno){
        AlumnoInformeDto informe = alumServ.informe(idAlumno);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(informe);
    }
}
