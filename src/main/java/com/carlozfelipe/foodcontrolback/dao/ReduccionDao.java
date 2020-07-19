package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.Producto;
import com.carlozfelipe.foodcontrolback.entities.Reduccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReduccionDao extends JpaRepository<Reduccion, Long> {

}
