package com.carlozfelipe.foodcontrolback.entities;

import com.carlozfelipe.foodcontrolback.enums.UsuarioEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String celular;

    private String nombre;

    private String celular2;

    private UsuarioEnum tipo;

    private String pass;

    private Date fechaNacimiento;


}
