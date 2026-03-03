package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Curso;
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
    public ResponseEntity<List<Curso>> listarTodo(){
        return ResponseEntity.ok(cursoServ.listarTodo());
    }
    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curs){
        Curso curso = cursoServ.guardar(curs);
        if(curso==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(curso);
    }

    @PutMapping
    public ResponseEntity<Curso> actualizar(@RequestBody Curso cur){
        Curso curso = cursoServ.actualizar(cur);
        if(curso==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(curso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> eliminar(@PathVariable Long id){
        Curso curso = cursoServ.eliminar(id);
        if(curso==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(curso);
    }

    @GetMapping("/{idCurso}/alumnos")
    public ResponseEntity<List<Alumno>> buscarAlumnos(@PathVariable Long idCurso){
        List<Alumno> alumnos = cursoServ.obtenerAlumnosPorId(idCurso);
        if(alumnos==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity.ok(alumnos);
    }
}
