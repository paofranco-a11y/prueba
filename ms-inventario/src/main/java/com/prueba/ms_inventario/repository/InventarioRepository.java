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

    Optional<Inventario> findByProductoId(Integer productoId); // Query 1: buscar por id de producto


    @Query("SELECT i FROM Inventario i WHERE i.ubicacionBodega LIKE %:ubicacion%") // Query 2: busca el producto por ubicacion utilizando JPQL
    List<Inventario> buscarPorUbicacion(@Param("ubicacion") String ubicacion);
}