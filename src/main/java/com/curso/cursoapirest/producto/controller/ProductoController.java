package com.curso.cursoapirest.producto.controller;

import com.curso.cursoapirest.categoria.error.CategoriaNotFoundException;
import com.curso.cursoapirest.error.ApiError;
import com.curso.cursoapirest.producto.dto.CreateProductDTO;
import com.curso.cursoapirest.producto.dto.EditProductDTO;
import com.curso.cursoapirest.producto.dto.ProductDTO;
import com.curso.cursoapirest.categoria.repository.CategoriaRepositorio;
import com.curso.cursoapirest.producto.dto.converter.ProductDTOConverter;
import com.curso.cursoapirest.producto.error.ProductNotFoundException;
import com.curso.cursoapirest.producto.model.Producto;
import com.curso.cursoapirest.producto.repository.ProductoRepositorio;
import com.curso.cursoapirest.upload.StorageService;
import com.curso.cursoapirest.upload.controller.FicheroController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoRepositorio productoRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;
    private final StorageService storageService;

    private final ProductDTOConverter productDTOConverter;

    @CrossOrigin(origins = "http://localhost:9001")
    @GetMapping("/producto")
    public ResponseEntity<?> obtenerTodos() {
        List<Producto> productoList = productoRepositorio.findAll();
        if (productoList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<ProductDTO> productDTOList = productoList
                    .stream()
                    .map(productDTOConverter::converterDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productDTOList);
        }
    }

    @ApiOperation(value = "Obtener un producto por su ID",
            notes = "Proveed un mecanismo para obtener los datos de un producto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Producto.class),
            @ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/producto/{id}")
//    public ResponseEntity<?> obtenerUno(@PathVariable Long id){
    public Producto obtenerUno(@ApiParam(value = "ID del producto", required = true, type = "long") @PathVariable Long id) {
        return productoRepositorio.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping(value = "/producto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> nuevoProducto(@RequestPart("nuevo") CreateProductDTO nuevo,
                                           @RequestPart("file") MultipartFile file) {

        String urlImagen = null;

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            urlImagen = MvcUriComponentsBuilder
                    .fromMethodName(FicheroController.class,
                            "serveFile",
                            imagen,
                            null)
                    .build()
                    .toUriString();
        }

        categoriaRepositorio.findById(nuevo.getIdCategoria())
                .orElseThrow(() -> new CategoriaNotFoundException(nuevo.getIdCategoria()));

        Producto nuevoProducto = productDTOConverter.newProductoConvertEntity(nuevo);
        nuevoProducto.setImagen(urlImagen);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepositorio.save(nuevoProducto));

    }

    @PutMapping("/producto/{id}")
//    public ResponseEntity<?> editarProducto(@RequestBody EditProductDTO editar, @PathVariable Long id) {
//        return productoRepositorio.findById(id)
//                .map(p -> {
//                    p.setPrecio(editar.getPrecio());
//                    p.setIdCategoria(categoriaRepositorio.getById(editar.getIdCategoria()));
//                    return ResponseEntity.ok(productoRepositorio.save(p));
//                })
//                .orElseGet(() -> {
//                    return ResponseEntity.notFound().build();
//                });
//    }
    public Producto editarProducto(@RequestBody EditProductDTO editar, @PathVariable Long id) {
        return productoRepositorio.findById(id)
                .map(p -> {
                    p.setPrecio(editar.getPrecio());
                    p.setIdCategoria(categoriaRepositorio.getById(editar.getIdCategoria()));
                    return productoRepositorio.save(p);
                }).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable Long id) {
//        productoRepositorio.deleteById(id);
        Producto producto = productoRepositorio.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productoRepositorio.delete(producto);
        return ResponseEntity.noContent().build();
    }

}
