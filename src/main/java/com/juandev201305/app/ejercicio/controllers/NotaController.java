package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.NotaDto;
import com.juandev201305.app.ejercicio.dtos.NotaFormDto;
import com.juandev201305.app.ejercicio.services.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase RestController:
 * Tabla Nota
 */
@RestController
@RequestMapping("/nota")
@CrossOrigin(origins = "*")
public class NotaController {
    private final NotaService notaServ;
    public NotaController(NotaService nota){
        this.notaServ=nota;
    }
    @GetMapping
    public ResponseEntity<List<NotaDto>> listarTodo(){
        return ResponseEntity.ok(notaServ.listarTodo());
    }

    @PostMapping
    public ResponseEntity<NotaDto> crear(@RequestBody NotaFormDto notaForm){
        NotaDto notaDto = notaServ.guardar(notaForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaDto> actualizar(@RequestBody NotaFormDto notaForm, @PathVariable Long id){
        NotaDto notaDto = notaServ.guardar(notaForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiReponseStatusDto> eliminar(@PathVariable Long id){
        ApiReponseStatusDto response = notaServ.eliminar(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<NotaDto>> notasByDate (@RequestParam String fechaInicio, @RequestParam String fechaFin){
        List<NotaDto> notas = notaServ.notasPorFecha(fechaInicio,fechaFin);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notas);
    }
}
