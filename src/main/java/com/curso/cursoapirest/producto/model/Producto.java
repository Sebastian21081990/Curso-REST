package com.curso.cursoapirest.producto.model;

import com.curso.cursoapirest.categoria.model.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="producto")
public class Producto implements Serializable {

    @ApiModelProperty(value = "ID del producto", dataType = "long", example = "1", position = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Nombre del producto", dataType = "String", example = "Jamon iberico de bellota", position = 2)
    private String nombre;

    @ApiModelProperty(value = "Precio del producto", dataType = "float", example = "253.26", position = 3)
    private float precio;

    @ApiModelProperty(value = "Imagen del producto", dataType = "String", example = "http://www.midominio.com/files/12345-imagen.jpg", position = 4)
    private String imagen;

    @ApiModelProperty(value = "Categoria del producto", dataType = "Categoria", example = "1", position = 1)
    @ManyToOne
    @JoinColumn(name="idCategoria")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria idCategoria;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Producto producto = (Producto) o;
        return id != null && Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
