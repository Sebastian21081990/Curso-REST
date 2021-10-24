package com.curso.cursoapirest.producto.dto.converter;

import com.curso.cursoapirest.categoria.repository.CategoriaRepositorio;
import com.curso.cursoapirest.producto.dto.CreateProductDTO;
import com.curso.cursoapirest.producto.dto.EditProductDTO;
import com.curso.cursoapirest.producto.model.Producto;
import com.curso.cursoapirest.producto.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDTOConverter{

    private final ModelMapper modelMapper;

    public ProductDTO converterDto(Producto producto){
        return modelMapper.map(producto, ProductDTO.class);
    }

    public Producto newProductoConvertEntity(CreateProductDTO createProductDTO){
        return modelMapper.map(createProductDTO, Producto.class);
    }

}
