package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Ingrediente {

    @Id
    @GeneratedValue
    private Long id;
    private Integer cantidad;
    @JoinColumn
    @ManyToOne
    private IngredienteBase ingredienteBase;
    @JoinColumn
    @ManyToOne
    private ProductoBase productoBase;
}
