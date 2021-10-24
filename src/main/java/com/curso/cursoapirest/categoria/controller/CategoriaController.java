package com.curso.cursoapirest.categoria.controller;

import com.curso.cursoapirest.categoria.error.CategoriaNotFoundException;
import com.curso.cursoapirest.categoria.model.Categoria;
import com.curso.cursoapirest.categoria.repository.CategoriaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepositorio categoriaRepositorio;

    @GetMapping("/categoria")
    public ResponseEntity<?> findAll() {

        List<Categoria> categoriaList = categoriaRepositorio.findAll();

        if (categoriaList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(categoriaList);
        }

    }

    @GetMapping("/categoria/{id}")
    public Categoria findById(@PathVariable Long id) {
        return categoriaRepositorio.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    @PostMapping("/categoria")
    public ResponseEntity<?> save(@RequestBody Categoria nueva) {
        Categoria categoria = categoriaRepositorio.save(nueva);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping("/categoria/{id}")
    public ResponseEntity<?> update(@RequestBody Categoria editada,
                                    @PathVariable Long id) {
        return categoriaRepositorio.findById(id)
                .map(categoria -> {
                    categoria.setNombre(editada.getNombre());
                    return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepositorio.save(categoria));
                })
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return categoriaRepositorio.findById(id)
                .map(categoria -> {
                    categoriaRepositorio.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

}
