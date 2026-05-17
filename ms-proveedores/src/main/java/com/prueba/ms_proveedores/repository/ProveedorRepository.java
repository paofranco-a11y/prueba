package com.prueba.ms_proveedores.repository;


import com.prueba.ms_proveedores.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    @Query(value = "SELECT p.id, p.nombre, p.rut, p.calificacion, p.activo, p.contacto_email " +
            "FROM proveedor p " +
            "WHERE p.activo = true " +
            "ORDER BY p.nombre ASC",
            nativeQuery = true)
    List<Proveedor> findProveedoresActivosOrdenados();
}