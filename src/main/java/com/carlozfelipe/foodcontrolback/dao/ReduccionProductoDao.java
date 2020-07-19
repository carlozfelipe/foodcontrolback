package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.Producto;
import com.carlozfelipe.foodcontrolback.entities.ReduccionProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReduccionProductoDao extends JpaRepository<ReduccionProducto, Long> {
    List<ReduccionProducto> findByProducto(Producto producto);
}
