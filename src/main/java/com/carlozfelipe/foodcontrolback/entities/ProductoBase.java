package com.carlozfelipe.foodcontrolback.entities;

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

    private TipoProductoBase tipo;

    private String nombre;

    private Integer valor;

    private String tamanio;

    private boolean inactivo;

    @Transient
    private List<Ingrediente> ingredientes;
}
