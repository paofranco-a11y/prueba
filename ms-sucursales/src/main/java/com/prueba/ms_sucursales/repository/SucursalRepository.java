package com.prueba.ms_sucursales.repository;

import com.prueba.ms_sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {


    @Query(value = "SELECT s.* FROM sucursal s " +
            "INNER JOIN region r ON s.region_id = r.id " +
            "WHERE r.nombre = :nombreRegion",
            nativeQuery = true)
    List<Sucursal> findSucursalesByNombreRegion(@Param("nombreRegion") String nombreRegion);
}