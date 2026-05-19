package com.prueba.ms_inventario.repository;


import com.prueba.ms_inventario.model.Inventario;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    Optional<Inventario> findByProductoId(Integer productoId); // query 1 busca por id del producto


    List<Inventario> findByCantidadDisponibleGreaterThanAndActivoTrue(Integer cantidadMinima);
}