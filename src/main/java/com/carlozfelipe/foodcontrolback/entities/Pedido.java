package com.carlozfelipe.foodcontrolback.entities;

import com.carlozfelipe.foodcontrolback.enums.EstadoPedidoEnum;
import com.carlozfelipe.foodcontrolback.enums.TipoPedidoEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue
    private Long id;
    private String mesa;
    @JoinColumn
    @ManyToOne
    private Usuario usuario;
    private Date fecha;
    private Short consecutivo;
    private Integer valor;
    private EstadoPedidoEnum estadoPedido;
    private Boolean pagado;
    private TipoPedidoEnum tipoPedidoEnum;
    private String detalle;
    @JoinColumn
    @ManyToOne
    private Direccion direccion;
    private Long fechaTimeStamp;
    @Transient
    private List<Producto> productos;
}
