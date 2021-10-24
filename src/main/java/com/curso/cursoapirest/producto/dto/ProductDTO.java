package com.curso.cursoapirest.producto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private long id;
    private String nombre;
    private String imagen;
    private float precio;

}
