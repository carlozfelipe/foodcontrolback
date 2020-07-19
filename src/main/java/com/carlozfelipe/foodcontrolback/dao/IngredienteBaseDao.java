package com.carlozfelipe.foodcontrolback.dao;

import com.carlozfelipe.foodcontrolback.entities.IngredienteBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteBaseDao extends JpaRepository<IngredienteBase, Long> {
}
