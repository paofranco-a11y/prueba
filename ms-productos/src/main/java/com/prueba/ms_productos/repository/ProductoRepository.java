package com.prueba.ms_productos.repository;

import com.prueba.ms_productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Query Method automático: sin @Query ni SQL manual.
    // 'ContainingIgnoreCase' busca el texto parcial ignorando mayúsculas/minúsculas
    // 'AndPrecioLessThan' filtra por precios menores al valor indicado
    List<Producto> findByNombreContainingIgnoreCaseAndPrecioLessThan(String nombre, Double precioMaximo);
}