package com.carlozfelipe.foodcontrolback.entities;

import com.carlozfelipe.foodcontrolback.enums.ProductoBaseEnum;
import com.carlozfelipe.foodcontrolback.enums.TamanioProductoBaseEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Data
@Entity
public class ProductoBase {

    @Id
    @GeneratedValue
    private Long id;

    private ProductoBaseEnum tipo;

    private String nombre;

    private Integer valor;

    private TamanioProductoBaseEnum tamanio;

    private boolean inactivo;

    @Transient
    private List<Ingrediente> ingredientes;
}
