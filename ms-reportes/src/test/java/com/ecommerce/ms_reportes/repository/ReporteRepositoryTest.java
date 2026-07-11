package com.ecommerce.ms_reportes.repository;

import com.ecommerce.ms_reportes.model.Reporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ReporteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReporteRepository reporteRepository;

    private Reporte reporteConsolidadoEnRango;
    private Reporte reporteNoConsolidadoEnRango;
    private Reporte reporteFueraDeRango;

    @BeforeEach
    void setUp() {
        reporteConsolidadoEnRango = new Reporte(
                null, "Reporte Consolidado Julio", "VENTAS", 20000f, 150, true, LocalDate.of(2026, 7, 5));

        reporteNoConsolidadoEnRango = new Reporte(
                null, "Reporte Sin Consolidar Julio", "VENTAS", 5000f, 30, false, LocalDate.of(2026, 7, 10));

        reporteFueraDeRango = new Reporte(
                null, "Reporte Junio", "VENTAS", 18000f, 100, true, LocalDate.of(2026, 6, 15));

        Reporte reporteInventarioBajoMonto = new Reporte(
                null, "Reporte Inventario", "INVENTARIO", 500f, 10, true, LocalDate.of(2026, 7, 5));

        Reporte reporteVentasAltoMonto = new Reporte(
                null, "Reporte Ventas Alto", "VENTAS", 50000f, 300, true, LocalDate.of(2026, 7, 5));

        entityManager.persist(reporteConsolidadoEnRango);
        entityManager.persist(reporteNoConsolidadoEnRango);
        entityManager.persist(reporteFueraDeRango);
        entityManager.persist(reporteInventarioBajoMonto);
        entityManager.persist(reporteVentasAltoMonto);
        entityManager.flush();
    }

    // ============ buscarReportesPorRangoYConsolidacion ============

    @Test
    void buscarReportesPorRangoYConsolidacion_deberiaFiltrarPorRangoDeFechasYConsolidacion() {
        List<Reporte> resultado = reporteRepository.buscarReportesPorRangoYConsolidacion(
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 31), true);

        // De los 5 persistidos, solo 3 caen en julio con esConsolidado=true:
        // reporteConsolidadoEnRango, reporteInventarioBajoMonto, reporteVentasAltoMonto
        assertThat(resultado).hasSize(3);
        assertThat(resultado)
                .extracting(Reporte::getTitulo)
                .contains("Reporte Consolidado Julio", "Reporte Inventario", "Reporte Ventas Alto");
    }

    @Test
    void buscarReportesPorRangoYConsolidacion_deberiaExcluirNoConsolidados() {
        List<Reporte> resultado = reporteRepository.buscarReportesPorRangoYConsolidacion(
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 31), false);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getTitulo()).isEqualTo("Reporte Sin Consolidar Julio");
    }

    @Test
    void buscarReportesPorRangoYConsolidacion_deberiaExcluirFechasFueraDeRango() {
        List<Reporte> resultado = reporteRepository.buscarReportesPorRangoYConsolidacion(
                LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 31), true);

        assertThat(resultado)
                .extracting(Reporte::getTitulo)
                .doesNotContain("Reporte Junio");
    }

    @Test
    void buscarReportesPorRangoYConsolidacion_deberiaRetornarVacio_cuandoNoHayCoincidencias() {
        List<Reporte> resultado = reporteRepository.buscarReportesPorRangoYConsolidacion(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31), true);

        assertThat(resultado).isEmpty();
    }

    // ============ findByTipoReporteAndTotalIngresosGreaterThanEqual ============

    @Test
    void findByTipoReporteAndTotalIngresosGreaterThanEqual_deberiaFiltrarPorTipoYMonto() {
        List<Reporte> resultado = reporteRepository
                .findByTipoReporteAndTotalIngresosGreaterThanEqual("VENTAS", 15000f);

        // De los reportes VENTAS, solo Consolidado Julio (20000) y Ventas Alto (50000)
        // superan o igualan 15000. Reporte Junio (18000) también califica.
        assertThat(resultado).hasSize(3);
        assertThat(resultado).allMatch(r -> r.getTipoReporte().equals("VENTAS"));
        assertThat(resultado).allMatch(r -> r.getTotalIngresos() >= 15000f);
    }

    @Test
    void findByTipoReporteAndTotalIngresosGreaterThanEqual_deberiaExcluirOtroTipo() {
        List<Reporte> resultado = reporteRepository
                .findByTipoReporteAndTotalIngresosGreaterThanEqual("VENTAS", 0f);

        assertThat(resultado)
                .extracting(Reporte::getTitulo)
                .doesNotContain("Reporte Inventario");
    }

    @Test
    void findByTipoReporteAndTotalIngresosGreaterThanEqual_deberiaRetornarVacio_cuandoMontoEsMuyAlto() {
        List<Reporte> resultado = reporteRepository
                .findByTipoReporteAndTotalIngresosGreaterThanEqual("VENTAS", 999999f);

        assertThat(resultado).isEmpty();
    }

    // ============ CRUD básico heredado de JpaRepository ============

    @Test
    void save_deberiaPersistirReporteCorrectamente() {
        Reporte nuevo = new Reporte(null, "Reporte Nuevo", "LOGISTICA", 3000f, 25, false, LocalDate.now());

        Reporte guardado = reporteRepository.save(nuevo);

        assertThat(guardado.getId()).isNotNull();
        assertThat(reporteRepository.findById(guardado.getId())).isPresent();
    }
}
