package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.models.Asignatura;
import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.services.AsignaturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignatura")
@CrossOrigin(origins = "*")
public class AsignaturaController {
    private final AsignaturaService asigServ;
    public AsignaturaController(AsignaturaService asig){
        this.asigServ=asig;
    }
    @GetMapping
    public ResponseEntity<List<Asignatura>> listarTodo(){
        return ResponseEntity.ok(asigServ.listarTodo());
    }
    @PostMapping
    public ResponseEntity<Asignatura> crear(@RequestBody Asignatura as){
        Asignatura asignatura = asigServ.guardar(as);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(asignatura);
    }

    @PutMapping
    public ResponseEntity<Asignatura> actualizar(@RequestBody Asignatura asig){
        Asignatura asignatura =  asigServ.actualizar(asig);
        if(asignatura==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(asignatura);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Asignatura> eliminar(@PathVariable Long id){
        Asignatura asignatura =  asigServ.eliminar(id);
        if(asignatura==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(asignatura);
    }

    @GetMapping("/{idAsignatura}/notas")
    public ResponseEntity<List<Nota>> notasPorAsignatura(@PathVariable Long idAsignatura){
        List<Nota> notas = asigServ.notasPorAsignatura(idAsignatura);
        if(notas==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notas);
    }
}
