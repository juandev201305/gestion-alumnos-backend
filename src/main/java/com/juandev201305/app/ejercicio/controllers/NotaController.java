package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.models.Nota;
import com.juandev201305.app.ejercicio.services.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/nota")
@CrossOrigin(origins = "*")
public class NotaController {
    private final NotaService notaServ;
    public NotaController(NotaService nota){
        this.notaServ=nota;
    }
    @GetMapping
    public ResponseEntity<List<Nota>> listarTodo(){
        return ResponseEntity.ok(notaServ.listarTodo());
    }
    @PostMapping
    public ResponseEntity<Nota> crear(@RequestBody Nota not){
        Nota nota = notaServ.guardar(not);
        if(nota==null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nota);
    }

    @PutMapping
    public ResponseEntity<Nota> actualizar(@RequestBody Nota no){
        Nota nota = notaServ.actualizar(no);
        if(nota==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Nota> eliminar(@PathVariable Long id){
        Nota nota = notaServ.eliminar(id);
        if(nota==null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(nota);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Nota>> notas (@RequestParam String fechaInicio, @RequestParam String fechaFin){
        List<Nota> notas = notaServ.notasPorFecha(fechaInicio,fechaFin);
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
