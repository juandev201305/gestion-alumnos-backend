package com.juandev201305.app.ejercicio.controllers;

import com.juandev201305.app.ejercicio.dtos.ApiReponseStatusDto;
import com.juandev201305.app.ejercicio.dtos.AsignaturaDto;
import com.juandev201305.app.ejercicio.dtos.AsignaturaFormDto;
import com.juandev201305.app.ejercicio.dtos.NotaDto;
import com.juandev201305.app.ejercicio.services.AsignaturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase RestController:
 * Tabla Asignatura
 */
@RestController
@RequestMapping("/asignatura")
@CrossOrigin(origins = "*")
public class AsignaturaController {
    private final AsignaturaService asigServ;
    public AsignaturaController(AsignaturaService asig){
        this.asigServ=asig;
    }
    @GetMapping
    public ResponseEntity<List<AsignaturaDto>> listarTodo(){
        return ResponseEntity.ok(asigServ.listarTodo());
    }
    @PostMapping
    public ResponseEntity<AsignaturaDto> crear(@RequestBody AsignaturaFormDto asignaturaForm){
        AsignaturaDto asignaturaDto = asigServ.guardar(asignaturaForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(asignaturaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignaturaDto> actualizar(@RequestBody AsignaturaFormDto asignaturaForm, @PathVariable Long id){
        AsignaturaDto asignaturaDto =  asigServ.actualizar(asignaturaForm, id);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(asignaturaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiReponseStatusDto> eliminar(@PathVariable Long id){
        ApiReponseStatusDto response =  asigServ.eliminar(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAsignatura}/notas")
    public ResponseEntity<List<NotaDto>> notasPorAsignatura(@PathVariable Long idAsignatura){
        List<NotaDto> notas = asigServ.notasPorAsignatura(idAsignatura);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notas);
    }
}
