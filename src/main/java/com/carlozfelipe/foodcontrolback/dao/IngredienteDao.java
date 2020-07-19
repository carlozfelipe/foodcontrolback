package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.Ingrediente;
import com.carlozfelipe.foodcontrolback.entities.ProductoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteDao extends JpaRepository<Ingrediente, Long> {
    List<Ingrediente> findByProductoBase(ProductoBase productoBase);
}
