package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Reduccion {
    @Id
    @GeneratedValue
    private Long id;
    @JoinColumn
    @ManyToOne
    private IngredienteBase ingredienteBase;
    private Integer valor;
}
