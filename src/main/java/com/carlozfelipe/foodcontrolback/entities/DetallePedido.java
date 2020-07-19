package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class DetallePedido {
    @Id
    @GeneratedValue
    private Long id;
    @JoinColumn
    @ManyToOne
    private Producto producto;
    @JoinColumn
    @ManyToOne
    private Pedido pedido;
}
