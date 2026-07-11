package com.prueba.ms_proveedores.repository;

import com.prueba.ms_proveedores.model.Contrato;
import com.prueba.ms_proveedores.model.Proveedor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Usa las credenciales del entorno 'test' de tu application.yml
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Evita el error de "Failed to replace DataSource"
public class ContratoRepositoryTest {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Test
    void testGuardarYBuscarContrato_Exitoso() {
        // 1. Crear y persistir el Proveedor (entidad padre) para respetar la clave foránea (FK)
        Proveedor proveedor = new Proveedor(null, "Proveedor de Pruebas SpA", "77.777.777-7", 4, true, "test@contratos.cl", null);
        Proveedor proveedorGuardado = proveedorRepository.save(proveedor);

        // 2. Instanciar el contrato asociándolo al proveedor ya guardado en la base de datos
        Contrato contrato = new Contrato(
                null,
                "CON-TEST-2026",
                2500000.0,
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                true,
                proveedorGuardado
        );

        // 3. Persistir el contrato en la base de datos física de pruebas 'prueba1_test'
        Contrato contratoGuardado = contratoRepository.save(contrato);

        // 4. Buscar el contrato guardado por su ID generado automáticamente
        Optional<Contrato> encontrado = contratoRepository.findById(contratoGuardado.getId());

        // 5.
        assertTrue(encontrado.isPresent(), "El contrato debería existir en la base de datos");
        assertEquals("CON-TEST-2026", encontrado.get().getCodigoContrato());
        assertEquals(2500000.0, encontrado.get().getMontoTotal());
        assertEquals(proveedorGuardado.getId(), encontrado.get().getProveedor().getId(), "La relación FK con el proveedor no coincide");
    }
}