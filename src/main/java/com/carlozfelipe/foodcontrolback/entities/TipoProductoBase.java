package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Data
public class TipoProductoBase {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private String tamanios;
    @Transient
    private List<String> listaTamanios;
    private String proporcion;
    @Transient
    private List<String> listaPropociones;
}
