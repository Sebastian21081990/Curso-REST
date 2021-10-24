package com.curso.cursoapirest.producto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDTO {

    private long id;
    private String nombre;
    private float precio;
    private long idCategoria;

}
