package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.Pedido;
import com.carlozfelipe.foodcontrolback.enums.EstadoPedidoEnum;
import com.carlozfelipe.foodcontrolback.negocio.DO.ReporteTemporalDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PedidoDao extends JpaRepository<Pedido, Long> {
        List<Pedido> findByEstadoPedidoAndFechaTimeStampGreaterThan(EstadoPedidoEnum estadoPedido, Long fechaTimeStamp);

        @Query(value = "SELECT new com.carlozfelipe.foodcontrolback.negocio.DO.ReporteTemporalDO(p.fecha, sum(p.valor))" +
                " FROM Pedido p WHERE p.fecha BETWEEN :fechaInicial AND :fechaFinal AND p.pagado = true group by fecha" +
                " order by fecha")
    List<ReporteTemporalDO> obtenerReportePorDias(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);

    @Query(value = "SELECT new com.carlozfelipe.foodcontrolback.negocio.DO.ReporteTemporalDO(sum(p.valor))" +
            " FROM Pedido p WHERE p.fecha BETWEEN :fechaInicial AND :fechaFinal AND p.pagado = true")
    List<ReporteTemporalDO> obtenerReportePorMes(@Param("fechaInicial") Date fechaInicial, @Param("fechaFinal") Date fechaFinal);

    List<Pedido> findByPagado(Boolean pagado);
    //LessThan
}
