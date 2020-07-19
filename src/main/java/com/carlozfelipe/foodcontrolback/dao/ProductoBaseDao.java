package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.ProductoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoBaseDao extends JpaRepository<ProductoBase, Long> {
}
