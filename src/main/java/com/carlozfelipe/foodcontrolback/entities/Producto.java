package com.carlozfelipe.foodcontrolback.entities;

import com.carlozfelipe.foodcontrolback.enums.ProporcionProductoEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue
    private Long id;
    @JoinColumn
    @ManyToOne
    private ProductoBase productoBase;
    private ProporcionProductoEnum proporcionProductoEnum;
    private String detalle;
    private Integer valor;
    @Transient
    private List<Adicion> adiciones;
    @Transient
    private  List<Reduccion> reducciones;
}
