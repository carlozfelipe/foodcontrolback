package com.carlozfelipe.foodcontrolback.rest;

import com.carlozfelipe.foodcontrolback.entities.Pedido;
import com.carlozfelipe.foodcontrolback.entities.Producto;
import com.carlozfelipe.foodcontrolback.enums.EstadoPedidoEnum;
import com.carlozfelipe.foodcontrolback.negocio.PedidoNegocio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PedidoRest {

    @Autowired
    PedidoNegocio pedidoNegocio;

    @GetMapping
    public List<Pedido> obtener(){
        return pedidoNegocio.obtener();
    }



    @GetMapping("/estado/{estadoPedido}/{fechaTimeStamp}")
    public List<Pedido> obtener(@PathVariable EstadoPedidoEnum estadoPedido, @PathVariable Long fechaTimeStamp){

        return pedidoNegocio.obtenerPorEstadoPedidoYDespuesDeFecha(estadoPedido, fechaTimeStamp);
    }

    @GetMapping("/pendientepago")
    public List<Pedido> obtenerPendientePago(){
        return pedidoNegocio.obtenerPendientePago();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void crear(@RequestBody Pedido pedido){
        pedidoNegocio.crear(pedido);
    }

    @PutMapping
    public Pedido modificar(@RequestBody Pedido pedido){
        return pedidoNegocio.modificar(pedido);
    }

    @PutMapping("/estado")
    public Pedido modificarEstado(@RequestBody Pedido pedido){
        return pedidoNegocio.modificarEstado(pedido);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        pedidoNegocio.eliminar(id);
        }

    @PostMapping("/{idPedido}/producto")
    public Producto addProducto(@RequestBody Producto producto, @PathVariable Long idPedido){
        return pedidoNegocio.addProducto(producto, idPedido);
    }

    @DeleteMapping("/{idPedido}/producto/{idProducto}")
    public void delProducto(@PathVariable Long idPedido, @PathVariable Long idProducto){
        pedidoNegocio.delProducto(idProducto, idPedido);
    }
}
