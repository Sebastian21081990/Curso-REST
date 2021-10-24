package com.curso.cursoapirest.upload.controller;

import com.curso.cursoapirest.categoria.error.CategoriaNotFoundException;
import com.curso.cursoapirest.categoria.repository.CategoriaRepositorio;
import com.curso.cursoapirest.producto.dto.CreateProductDTO;
import com.curso.cursoapirest.producto.dto.EditProductDTO;
import com.curso.cursoapirest.producto.dto.ProductDTO;
import com.curso.cursoapirest.producto.dto.converter.ProductDTOConverter;
import com.curso.cursoapirest.producto.error.ProductNotFoundException;
import com.curso.cursoapirest.producto.model.Producto;
import com.curso.cursoapirest.producto.repository.ProductoRepositorio;
import com.curso.cursoapirest.upload.StorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FicheroController {

    private final StorageService storageService;
    private static final Logger logger = LoggerFactory.getLogger(FicheroController.class);

    @GetMapping(value = "/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename,
                                              HttpServletRequest request){

        Resource file = storageService.loadAsResource(filename);


        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        }catch (IOException ex){
            logger.info("Could not determine file type.");
        }

        if(contentType == null){
            contentType = "applition/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);

    }

}
