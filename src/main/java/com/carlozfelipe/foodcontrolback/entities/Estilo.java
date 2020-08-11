package com.carlozfelipe.foodcontrolback.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class Estilo {
    @Id
    @GeneratedValue
    private Long id;
    private String colorPrimario;
    private String colorSecundario;
    private String nombre;
    private String tipoBoton;
    @Lob
    private byte[] logo;
}
