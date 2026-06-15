package com.ecommerce.ms_reportes.repository;

import com.ecommerce.ms_reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Integer> {


    @Query("SELECT r FROM Reporte r WHERE r.fechaGeneracion BETWEEN :inicio AND :fin AND r.esConsolidado = :esConsolidado")
    List<Reporte> buscarReportesPorRangoYConsolidacion(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin,
            @Param("esConsolidado") boolean esConsolidado
    );

    List<Reporte> findByTipoReporteAndTotalIngresosGreaterThanEqual(String tipoReporte, Float monto);
}
