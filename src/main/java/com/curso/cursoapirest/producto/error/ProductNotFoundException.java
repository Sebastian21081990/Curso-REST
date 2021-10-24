package com.curso.cursoapirest.producto.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id){
        super("No se puede encontrar el producto con la ID: " + id);
    }

}
