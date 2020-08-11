package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue
    private Long id;
    private String detalle;
    private Integer valor;
    @Transient
    private List<Adicion> adiciones;
    @Transient
    private  List<Reduccion> reducciones;
    @Transient
    private List<ProductoBase> productoBase;
}
