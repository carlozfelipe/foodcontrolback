package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.AdicionProducto;
import com.carlozfelipe.foodcontrolback.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdicionProductoDao extends JpaRepository<AdicionProducto, Long> {
    List<AdicionProducto> findByProducto(Producto producto);
}
