package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Fidelizacion {
    @Id
    @GeneratedValue
    private Long id;
    @JoinColumn
    @OneToOne
    private Usuario usuario;
    private Date fechaCumpleanios;
    private Integer totalCompras;
    private Date fechaUltimoPedido;
}
