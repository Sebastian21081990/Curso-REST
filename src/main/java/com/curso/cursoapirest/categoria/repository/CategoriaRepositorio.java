package com.curso.cursoapirest.categoria.repository;

import com.curso.cursoapirest.categoria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
}
