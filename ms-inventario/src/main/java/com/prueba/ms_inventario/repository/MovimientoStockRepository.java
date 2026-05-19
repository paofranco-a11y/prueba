package com.prueba.ms_inventario.repository;


import com.prueba.ms_inventario.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoStockRepository  extends JpaRepository<MovimientoStock, Integer> {

}
