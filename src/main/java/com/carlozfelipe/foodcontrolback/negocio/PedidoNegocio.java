package com.carlozfelipe.foodcontrolback.negocio;

import com.carlozfelipe.foodcontrolback.dao.*;
import com.carlozfelipe.foodcontrolback.entities.*;
import com.carlozfelipe.foodcontrolback.enums.EstadoPedidoEnum;
import com.carlozfelipe.foodcontrolback.util.UtilidadFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PedidoNegocio {
    @Autowired
    private PedidoDao pedidoDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private DetallePedidoDao detallePedidoDao;
    @Autowired
    private ProductoBaseDao productoBaseDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private AdicionDao adicionDao;
    @Autowired
    private ReduccionDao reduccionDao;
    @Autowired
    private AdicionProductoDao adicionProductoDao;
    @Autowired
    private ReduccionProductoDao reduccionProductoDao;
    @Autowired
    private DireccionDao direccionDao;
    @Autowired
    private FidelizacionDao fidelizacionDao;
    @Autowired
    private ProductoBaseProductoDao productoBaseProductoDao;

    public void crear(Pedido pedido) {
        Date fechaPedido = new Date();
        if (pedido.getUsuario() != null && pedido.getUsuario().getId() != null) {
            Usuario usuario = usuarioDao.findById(pedido.getUsuario().getId()).orElse(null);

            if (usuario != null) {
                Fidelizacion fidelizacion = fidelizacionDao.findByUsuario(usuario);
                if(fidelizacion.getFechaUltimoPedido()==null){
                    fidelizacion.setFechaUltimoPedido(fechaPedido);
                    fidelizacion.setTotalCompras(0);
                }
                if(UtilidadFecha.meses(fidelizacion.getFechaUltimoPedido(), fechaPedido )>=6){
                    fidelizacion.setTotalCompras(pedido.getValor());
                }else{
                    fidelizacion.setTotalCompras(fidelizacion.getTotalCompras()+pedido.getValor());
                }
                fidelizacion.setFechaUltimoPedido(fechaPedido);
                fidelizacionDao.save(fidelizacion);
                pedido.setUsuario(usuario);
            } else {
                throw new IllegalArgumentException("Usuario no existe");
            }
        } else {
            pedido.setUsuario(null);
        }
        if (pedido.getDireccion() != null) {
            Direccion direccion = direccionDao.findById(pedido.getDireccion().getId()).orElse(null);
            if (direccion != null) {
                if (pedido.getDireccion().getUsuario() != null && pedido.getDireccion().getUsuario().getId() != null) {
                    Usuario usuario = usuarioDao.findById(pedido.getDireccion().getUsuario().getId()).orElse(null);
                    if (usuario != null) {
                        direccion.setUsuario(usuario);
                    }
                }
                pedido.setDireccion(direccion);
            }
        }
        validarPedido(pedido);
        pedido.setEstadoPedido(EstadoPedidoEnum.PENDIENTE);
        pedido.setFecha(fechaPedido);
        pedido.setFechaTimeStamp((fechaPedido).getTime());
        pedido = pedidoDao.save(pedido);
        for (Producto producto : pedido.getProductos()) {
            producto = productoDao.save(producto);
            for (Adicion adicion : producto.getAdiciones()) {
                Adicion adicionNueva = adicionDao.findById(adicion.getId()).get();
                AdicionProducto adicionProducto = new AdicionProducto();
                adicionProducto.setAdicion(adicionNueva);
                adicionProducto.setProducto(producto);
                adicionProductoDao.save(adicionProducto);
            }
            for (Reduccion reduccion : producto.getReducciones()) {
                Reduccion reduccionNueva = reduccionDao.findById(reduccion.getId()).get();
                ReduccionProducto reduccionProducto = new ReduccionProducto();
                reduccionProducto.setReduccion(reduccionNueva);
                reduccionProducto.setProducto(producto);
                reduccionProductoDao.save(reduccionProducto);
            }
            for(ProductoBase productoBase : producto.getProductoBase()){
                ProductoBase productoBaseNuevo = productoBaseDao.findById(productoBase.getId()).get();
                ProductoBaseProducto productoBaseProducto = new ProductoBaseProducto();
                productoBaseProducto.setProductoBase(productoBaseNuevo);
                productoBaseProducto.setProducto(producto);
                productoBaseProductoDao.save(productoBaseProducto);
            }

            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedido);
            detallePedido.setProducto(producto);
            detallePedidoDao.save(detallePedido);

        }

    }

    private void validarPedido(Pedido pedido) {
        for (Producto producto : pedido.getProductos()) {
            for (Adicion adicion : producto.getAdiciones()) {
                if (!adicionDao.findById(adicion.getId()).isPresent()) {
                    throw new IllegalArgumentException("No existe adición ");
                }
            }
            for (Reduccion reduccion : producto.getReducciones()) {
                if (!reduccionDao.findById(reduccion.getId()).isPresent()) {
                    throw new IllegalArgumentException("No existe reducción ");
                }
            }
            for( ProductoBase productoBase : producto.getProductoBase()){
                if(!productoBaseDao.findById(productoBase.getId()).isPresent()){
                    throw new IllegalArgumentException("No existe producto base ");
                }
            }
        }
    }

    public Pedido modificar(Pedido pedido) {
        Pedido pedidoModificar = pedidoDao.findById(pedido.getId()).orElse(null);
        if (pedidoModificar != null) {
            pedidoModificar.setDireccion(pedido.getDireccion());
            pedidoModificar.setPagado(pedido.getPagado());
            pedidoModificar.setMesa(pedido.getMesa());
            pedidoModificar.setTipoPedidoEnum(pedido.getTipoPedidoEnum());
            if(!pedidoModificar.getValor().equals(pedido.getValor())){
                modificarValorFidelidad(pedidoModificar.getUsuario(), pedidoModificar.getValor(), pedido.getValor());
            }
            pedidoModificar.setTipoPedidoEnum(pedido.getTipoPedidoEnum());
            pedidoModificar.setDetalle(pedido.getDetalle());
            modificarProductos(pedido, pedidoModificar);

            return pedidoDao.save(pedidoModificar);
        }
        throw new IllegalArgumentException("Pedido no existe");
    }

    private void modificarValorFidelidad(Usuario usuario, Integer valorPedidoBD, Integer valorPedidoModificado) {
        if(usuario!=null){
            Fidelizacion fidelizacion = fidelizacionDao.findByUsuario(usuario);
            fidelizacion.setTotalCompras(fidelizacion.getTotalCompras()+valorPedidoModificado-valorPedidoBD);
            fidelizacionDao.save(fidelizacion);
        }
    }

    private void modificarProductos(Pedido pedido, Pedido pedidoModificar) {
        if(pedido.getProductos()!=null) {
            for (Producto producto : pedido.getProductos()) {
                List<Adicion> listaAdiciones = producto.getAdiciones();
                List<Reduccion> listaReducciones = producto.getReducciones();
                List<AdicionProducto> listaAdicionesProducto = adicionProductoDao.findByProducto(producto);
                List<ReduccionProducto> listaReduccionProducto = reduccionProductoDao.findByProducto(producto);
                modificarAdiciones(listaAdiciones, listaAdicionesProducto, producto);
                modificarReduccciones(listaReducciones, listaReduccionProducto, producto);
            }
        }
        //pedidoModificar.setProductos(pedido.getProductos());
        }

    private void modificarReduccciones(List<Reduccion> listaReducciones, List<ReduccionProducto> listaReduccionProducto, Producto producto) {
        adicionarReducciones(listaReducciones, listaReduccionProducto, producto);
        eliminarReduccines(listaReducciones, listaReduccionProducto, producto);
    }

    private void eliminarReduccines(List<Reduccion> listaReducciones, List<ReduccionProducto> listaReduccionProducto, Producto producto) {
        for(ReduccionProducto reduccionProducto:listaReduccionProducto){
            boolean eliminar = true;
            for(Reduccion reduccion:listaReducciones){
                if(reduccion.getId().equals(reduccionProducto.getReduccion().getId())){
                    eliminar = false;
                    break;
                }
            }
            if(eliminar){
                reduccionProductoDao.delete(reduccionProducto);
            }
        }
    }

    private void adicionarReducciones(List<Reduccion> listaReducciones, List<ReduccionProducto> listaReduccionProducto, Producto producto) {
        for(Reduccion reduccion:listaReducciones){
            boolean adicionar = true;
            for(ReduccionProducto reduccionProducto:listaReduccionProducto){
                if(reduccion.getId().equals(reduccionProducto.getReduccion().getId())){
                    adicionar = false;
                    break;
                }
            }
            if(adicionar){
                ReduccionProducto reduccionProducto = new ReduccionProducto();
                reduccionProducto.setProducto(producto);
                reduccionProducto.setReduccion(reduccion);
                reduccionProductoDao.save(reduccionProducto);
            }
        }
    }

    private void modificarAdiciones(List<Adicion> listaAdiciones, List<AdicionProducto> listaAdicionesProducto,
                                                 Producto producto) {
        adicionarAdiciones(listaAdiciones, listaAdicionesProducto, producto);
        elimninarAdiciones(listaAdiciones, listaAdicionesProducto, producto);

    }

    private void elimninarAdiciones(List<Adicion> listaAdiciones, List<AdicionProducto> listaAdicionesProducto, Producto producto) {
        for(AdicionProducto adicionProducto:listaAdicionesProducto){
            boolean eliminar = true;
            for (Adicion adicion:listaAdiciones){
                if(adicionProducto.getAdicion().getId().equals(adicion.getId())){
                    eliminar = false;
                    break;
                }
            }
            if (eliminar){
                adicionProductoDao.delete(adicionProducto);
            }
        }
    }

    private void adicionarAdiciones(List<Adicion> listaAdiciones, List<AdicionProducto> listaAdicionesProducto, Producto producto) {
        for(Adicion adicion:listaAdiciones){
            boolean adicionar = true;
            for(AdicionProducto adicionProducto:listaAdicionesProducto){
                if(adicion.getId().equals(adicionProducto.getAdicion().getId())){
                    adicionar = false;
                    break;
                }
            }
            if(adicionar){
                AdicionProducto adicionProducto = new AdicionProducto();
                adicionProducto.setProducto(producto);
                adicionProducto.setAdicion(adicion);
                adicionProductoDao.save(adicionProducto);
            }
        }
    }


    public List<Pedido> obtener() {
        return pedidoDao.findAll();
    }

    public void eliminar(Long id) {
        Pedido pedido = pedidoDao.findById(id).orElse(null);
        if (pedido != null) {
            List<DetallePedido> detallePedidoList=detallePedidoDao.findByPedido(pedido);
            for (DetallePedido detallePedido:detallePedidoList
                 ) {
                List<AdicionProducto> adicionProductoList= adicionProductoDao.findByProducto(detallePedido.getProducto());
                List<ReduccionProducto> reduccionProductoList= reduccionProductoDao.findByProducto(detallePedido.getProducto());
                for (AdicionProducto adicionProducto:adicionProductoList
                     ) {
                    adicionProductoDao.delete(adicionProducto);
                }
                for (ReduccionProducto reduccionProducto:reduccionProductoList
                     ) {
                    reduccionProductoDao.delete(reduccionProducto);
                }
                detallePedidoDao.delete(detallePedido);
                productoDao.delete(detallePedido.getProducto());
            }
            modificarValorFidelidad(pedido.getUsuario(), pedido.getValor(), 0);
            pedidoDao.delete(pedido);
        }
    }

    public List<Pedido> obtenerPorEstadoPedidoYDespuesDeFecha(EstadoPedidoEnum estadoPedido, Long fechaTimeStamp) {
        if(fechaTimeStamp == null || fechaTimeStamp == -1){
            fechaTimeStamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0,0))).getTime();
        }
        System.out.println("fechaTimeStamp: "+ fechaTimeStamp);
        List<Pedido> pedidoList = pedidoDao.findByEstadoPedidoAndFechaTimeStampGreaterThan(estadoPedido, fechaTimeStamp);
        List<Producto> productoList;
        Producto producto;
        for (Pedido pedido:pedidoList
             ) {
            productoList = new ArrayList<>();
            List<DetallePedido> detallePedidos = detallePedidoDao.findByPedido(pedido);
            for (DetallePedido detallePedido:detallePedidos
                 ) {
                producto = detallePedido.getProducto();
                List<AdicionProducto> adicionProductos = adicionProductoDao.findByProducto(producto);
                List<Adicion> adicions = new ArrayList<>();
                for (AdicionProducto adicionProducto:adicionProductos
                     ) {
                    adicions.add(adicionProducto.getAdicion());
                }
                producto.setAdiciones(adicions);
                List<ReduccionProducto> reduccionProductos = reduccionProductoDao.findByProducto(producto);
                List<Reduccion> reduccions = new ArrayList<>();
                for (ReduccionProducto reduccionProducto:reduccionProductos
                ) {
                    reduccions.add(reduccionProducto.getReduccion());
                }
                producto.setReducciones(reduccions);
                productoList.add(detallePedido.getProducto());
            }
            pedido.setProductos(productoList);
        }
        return pedidoList;
    }

    public Pedido modificarEstado(Pedido pedido) {
        Pedido pedidoModificar = pedidoDao.findById(pedido.getId()).orElse(null);
        if (pedidoModificar != null) {
            if (pedidoModificar.getEstadoPedido().equals(pedido.getEstadoPedido())) {
                throw new IllegalArgumentException("pedido ya esta en Estado " + pedidoModificar.getEstadoPedido());
            } else {
                pedidoModificar.setEstadoPedido(pedido.getEstadoPedido());
                return pedidoDao.save(pedidoModificar);
            }
        }
        throw new IllegalArgumentException("Pedido no existe");
    }


    public Producto addProducto(Producto producto, Long idPedido) {
        Pedido pedidoModificar = pedidoDao.findById(idPedido).orElse(null);

        if(pedidoModificar!=null){
            modificarValorFidelidad(pedidoModificar.getUsuario(), pedidoModificar.getValor(), pedidoModificar.getValor()+producto.getValor());
            pedidoModificar.setValor(pedidoModificar.getValor()+producto.getValor());
            producto = productoDao.save(producto);
            for (Adicion adicion : producto.getAdiciones()) {
                Adicion adicionNueva = adicionDao.findById(adicion.getId()).get();
                AdicionProducto adicionProducto = new AdicionProducto();
                adicionProducto.setAdicion(adicionNueva);
                adicionProducto.setProducto(producto);
                adicionProductoDao.save(adicionProducto);
            }
            for (Reduccion reduccion : producto.getReducciones()) {
                Reduccion reduccionNueva = reduccionDao.findById(reduccion.getId()).get();
                ReduccionProducto reduccionProducto = new ReduccionProducto();
                reduccionProducto.setReduccion(reduccionNueva);
                reduccionProducto.setProducto(producto);
                reduccionProductoDao.save(reduccionProducto);
            }
            for(ProductoBase productoBase : producto.getProductoBase()){
                ProductoBase productoBaseNuevo = productoBaseDao.findById(productoBase.getId()).get();
                ProductoBaseProducto productoBaseProducto = new ProductoBaseProducto();
                productoBaseProducto.setProductoBase(productoBaseNuevo);
                productoBaseProducto.setProducto(producto);
                productoBaseProductoDao.save(productoBaseProducto);
            }
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(pedidoModificar);
            detallePedido.setProducto(producto);
            detallePedidoDao.save(detallePedido);
            pedidoDao.save(pedidoModificar);
            return producto;
        }
        throw new IllegalArgumentException("Pedido no existe");
    }

    public void delProducto(Long idProducto, Long idPedido) {
        Pedido pedidoModificar = pedidoDao.findById(idPedido).orElse(null);
        Producto productoEliminar = productoDao.findById(idProducto).orElse(null);
        if(pedidoModificar!=null && productoEliminar!=null){
            modificarValorFidelidad(pedidoModificar.getUsuario(), pedidoModificar.getValor(),
                    pedidoModificar.getValor()-productoEliminar.getValor());
            pedidoModificar.setValor(pedidoModificar.getValor()-productoEliminar.getValor());
            List<AdicionProducto> adicionProductoList=adicionProductoDao.findByProducto(productoEliminar);
            for (AdicionProducto adicionProducto:adicionProductoList
                 ) {
                adicionProductoDao.delete(adicionProducto);
            }
            List<ReduccionProducto> reduccionProductoList = reduccionProductoDao.findByProducto(productoEliminar);
            for (ReduccionProducto reduccionProducto:reduccionProductoList
                 ) {
                reduccionProductoDao.delete(reduccionProducto);
            }
            DetallePedido detallePedido = detallePedidoDao.findByPedidoAndProducto(pedidoModificar, productoEliminar);
            detallePedidoDao.delete(detallePedido);
        }
        pedidoDao.save(pedidoModificar);
    }

    public List<Pedido> obtenerPendientePago() {
        return pedidoDao.findByPagado(Boolean.FALSE);
    }
}
