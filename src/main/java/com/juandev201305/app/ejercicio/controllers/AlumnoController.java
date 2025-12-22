package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.dtos.AlumnoDto;
import com.juandev201305.app.ejercicio.dtos.AlumnoInformeDto;
import com.juandev201305.app.ejercicio.models.Alumno;
import com.juandev201305.app.ejercicio.models.Curso;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.services.AlumnoService;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumno")
@CrossOrigin(origins = "*")
public class AlumnoController {
    private final AlumnoService alumServ;
    public AlumnoController(AlumnoService alumn){
        this.alumServ=alumn;
    }
    @GetMapping
    public ResponseEntity<List<Alumno>> listartodo(){
        return ResponseEntity.ok(alumServ.listarTodo());
    }

    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno al){
        Alumno alumno = alumServ.guardar(al);
        if(alumno==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumno);
    }

    @PutMapping
    public ResponseEntity<Alumno> actualizar(@RequestBody Alumno al){
        Alumno alumno = alumServ.guardar(al);
        if(alumno==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlumnoDto> eliminar(@PathVariable Long id){
        AlumnoDto alumno = alumServ.eliminar(id);
        if(alumno==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        if(alumno.getStatus().equals("209")){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(alumno);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alumno);
    }

    @GetMapping("/{idAlumno}/curso")
    public ResponseEntity<Curso> cursoDeAlumno(@PathVariable Long idAlumno){
        Curso curso = alumServ.buscarCursoDelAlumno(idAlumno);
        if(curso==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity.ok(curso);
    }

    @GetMapping("/{idAlumno}/notas")
    public ResponseEntity<List<Nota>> notasPorAlumnos(@PathVariable Long idAlumno){
        List<Nota> notas = alumServ.notaPorAlumno(idAlumno);
        if(notas==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity.ok(notas);
    }

    @GetMapping("/{idAlumno}/promedio")
    public ResponseEntity<Float> promedioGeneral(@PathVariable Long idAlumno){
        Float promedio = alumServ.promedioGeneralAlumno(idAlumno);
        if (promedio==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promedio);
    }

    @GetMapping("/{idAlumno}/promedio/asignatura/{idAsignatura}")
    public ResponseEntity<Float> promedioAsignatura(@PathVariable Long idAlumno, @PathVariable Long idAsignatura){
        Float promedio = alumServ.promedioAsignaturaAlumno(idAlumno,idAsignatura);
        if(promedio==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(0f);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promedio);
    }

    @GetMapping("/{idAlumno}/informe")
    public ResponseEntity<AlumnoInformeDto> informe (@PathVariable Long idAlumno){
        AlumnoInformeDto informe = alumServ.informe(idAlumno);
        if(informe==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(informe);
    }

    @GetMapping("/prueba")
    public ResponseEntity<List<Float>> xd (@RequestParam Long id, @RequestParam Long id2){
        return ResponseEntity.ok(alumServ.prueba(id,id2));
    }
}
