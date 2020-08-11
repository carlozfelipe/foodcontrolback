package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductoBaseProducto {
    @Id
    @GeneratedValue
    private Long id;
    @JoinColumn
    @ManyToOne
    ProductoBase productoBase;
    @JoinColumn
    Producto producto;
}
