package com.ecommerce.ms_empleados.repository;

import com.ecommerce.ms_empleados.model.Empleado;
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
class EmpleadoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @BeforeEach
    void setUp() {
        Empleado empleadoSucursal3_2024 = new Empleado(
                null, "Ana Torres", "ana.torres@ecommerce.com", 900000f, 3, true, LocalDate.of(2024, 5, 10));

        Empleado empleadoSucursal3_2023 = new Empleado(
                null, "Luis Rojas", "luis.rojas@ecommerce.com", 800000f, 3, true, LocalDate.of(2023, 8, 20));

        Empleado empleadoSucursal7_2024 = new Empleado(
                null, "Carla Díaz", "carla.diaz@ecommerce.com", 950000f, 7, true, LocalDate.of(2024, 1, 15));

        entityManager.persist(empleadoSucursal3_2024);
        entityManager.persist(empleadoSucursal3_2023);
        entityManager.persist(empleadoSucursal7_2024);
        entityManager.flush();
    }

    @Test
    void buscarEmpleadosPorSucursalYAnio_deberiaRetornarSoloLosQueCoinciden() {
        List<Empleado> resultado = empleadoRepository.buscarEmpleadosPorSucursalYAnio(3, 2024);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreCompleto()).isEqualTo("Ana Torres");
    }

    @Test
    void buscarEmpleadosPorSucursalYAnio_deberiaRetornarVacio_cuandoNoHayCoincidencias() {
        List<Empleado> resultado = empleadoRepository.buscarEmpleadosPorSucursalYAnio(99, 2024);

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarEmpleadosPorSucursalYAnio_deberiaFiltrarPorAnioCorrectamente() {
        List<Empleado> resultado = empleadoRepository.buscarEmpleadosPorSucursalYAnio(3, 2023);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreCompleto()).isEqualTo("Luis Rojas");
    }

    @Test
    void save_deberiaPersistirEmpleadoCorrectamente() {
        Empleado nuevo = new Empleado(
                null, "Pedro Gómez", "pedro.gomez@ecommerce.com", 700000f, 5, false, LocalDate.now());

        Empleado guardado = empleadoRepository.save(nuevo);

        assertThat(guardado.getId()).isNotNull();
        assertThat(empleadoRepository.findById(guardado.getId())).isPresent();
    }
}
