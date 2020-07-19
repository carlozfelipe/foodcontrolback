package com.carlozfelipe.foodcontrolback.entities;

import com.carlozfelipe.foodcontrolback.enums.ViaEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Direccion {
    @Id
    @GeneratedValue
    private Long id;

    ViaEnum tipo;
    Short numeroVia;
    String numeroCasa;
    String barrio;
    String referencia;
    Boolean activa;
    @JoinColumn
    @ManyToOne
    Usuario usuario;
}
