package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.DetallePedido;
import com.carlozfelipe.foodcontrolback.entities.Pedido;
import com.carlozfelipe.foodcontrolback.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallePedidoDao extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedido(Pedido pedido);

    DetallePedido findByPedidoAndProducto(Pedido pedido, Producto productoEliminar);

    void deleteByPedido(Pedido pedido);
}
