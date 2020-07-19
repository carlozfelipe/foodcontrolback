package com.carlozfelipe.foodcontrolback.entities;

import com.carlozfelipe.foodcontrolback.enums.IngredienteEnum;
import com.carlozfelipe.foodcontrolback.enums.UnidadMedidaEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;


@Data
@Entity
public class IngredienteBase {
    @Id
    @GeneratedValue
    private Long id;

    private IngredienteEnum tipo;

    private String nombre;

    private Integer valor;

    private UnidadMedidaEnum unidad;

    private boolean inactivo;

    @Transient
    private Integer valorAdicion;

    @Transient
    private Integer valorReduccion;
}
