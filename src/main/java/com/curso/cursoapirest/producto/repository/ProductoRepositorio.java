package com.curso.cursoapirest.producto.repository;

import com.curso.cursoapirest.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}
