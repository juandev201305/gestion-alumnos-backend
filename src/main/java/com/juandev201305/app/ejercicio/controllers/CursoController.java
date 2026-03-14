package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.dtos.AlumnoDto;
import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.CursoDto;
import com.juandev201305.app.ejercicio.dtos.CursoFormDto;
import com.juandev201305.app.ejercicio.services.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase RestController:
 * Tabla Curso
 */
@RestController
@RequestMapping("/curso")
@CrossOrigin(origins = "*")
public class CursoController {
    private final CursoService cursoServ;
    public CursoController(CursoService curso){
        this.cursoServ=curso;
    }

    @GetMapping
    public ResponseEntity<List<CursoDto>> listarTodo(){
        return ResponseEntity.ok(cursoServ.listarTodo());
    }
    @PostMapping
    public ResponseEntity<CursoDto> crear(@RequestBody CursoFormDto cursoForm){
        CursoDto cursoDto = cursoServ.guardar(cursoForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cursoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDto> actualizar(@RequestBody CursoFormDto cursoForm, Long id){
        CursoDto cursoDto = cursoServ.actualizar(cursoForm, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cursoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiReponseStatusDto> eliminar(@PathVariable Long id){
        ApiReponseStatusDto response = cursoServ.eliminar(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{idCurso}/alumnos")
    public ResponseEntity<List<AlumnoDto>> buscarAlumnos(@PathVariable Long idCurso){
        List<AlumnoDto> alumnos = cursoServ.obtenerAlumnosPorId(idCurso);
        return ResponseEntity.ok(alumnos);
    }
}
