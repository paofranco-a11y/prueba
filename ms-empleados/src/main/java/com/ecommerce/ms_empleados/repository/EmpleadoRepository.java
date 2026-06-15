package com.ecommerce.ms_empleados.repository;

import com.ecommerce.ms_empleados.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    /**
     * Native Query obligatoria exigida en la pauta de evaluación.
     * Lista empleados filtrados por sucursal e ingresados en un año determinado.
     */
    @Query(value = "SELECT * FROM empleados WHERE sucursal_id = :sucursalId AND YEAR(fecha_ingreso) = :anio", nativeQuery = true)
    List<Empleado> buscarEmpleadosPorSucursalYAnio(@Param("sucursalId") Integer sucursalId, @Param("anio") Integer anio);
}
