package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AdicionProducto {
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn
    @ManyToOne
    private Adicion adicion;

    @JoinColumn
    @ManyToOne
    private Producto producto;


}
