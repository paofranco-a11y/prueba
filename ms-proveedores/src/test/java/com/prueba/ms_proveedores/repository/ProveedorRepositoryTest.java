package com.prueba.ms_proveedores.repository;

import com.prueba.ms_proveedores.model.Proveedor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase; // 1. IMPORTAR ESTA CLASE
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 2. AGREGAR ESTA ANOTACIÓN CRÍTICA
public class ProveedorRepositoryTest {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Test
    void testFindProveedoresActivosOrdenados_DebeRetornarSoloRegistrosActivos() {
        Proveedor proveedorActivo = new Proveedor(null, "Empresa Activa S.A.", "11.111.111-1", 5, true, "contacto@activa.cl", null);
        Proveedor proveedorInactivo = new Proveedor(null, "Empresa Inactiva Ltda.", "22.222.222-2", 3, false, "contacto@inactiva.cl", null);

        proveedorRepository.save(proveedorActivo);
        proveedorRepository.save(proveedorInactivo);

        List<Proveedor> resultado = proveedorRepository.findProveedoresActivosOrdenados();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Empresa Activa S.A.", resultado.get(0).getNombre());
    }
}