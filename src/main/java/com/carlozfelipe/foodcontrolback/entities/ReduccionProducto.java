package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ReduccionProducto {
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn
    @ManyToOne
    private Reduccion reduccion;

    @JoinColumn
    @ManyToOne
    private Producto producto;


}
