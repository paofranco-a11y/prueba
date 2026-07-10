package com.prueba.ms_proveedores.runner;

import com.prueba.ms_proveedores.model.Contrato;
import com.prueba.ms_proveedores.model.Proveedor;
import com.prueba.ms_proveedores.repository.ContratoRepository;
import com.prueba.ms_proveedores.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class ProveedorRunner implements CommandLineRunner {

    private final ProveedorRepository proveedorRepository;
    private final ContratoRepository contratoRepository;

    @Override
    public void run(String... args) throws Exception {


            if (!proveedorRepository.existsById(1)) {
                proveedorRepository.save(new Proveedor(null, "Logística Central S.A.", "76.123.456-7", 5, true, "contacto@logisticacentral.cl", null));
            }
            if (!proveedorRepository.existsById(2)) {
                proveedorRepository.save(new Proveedor(null, "Distribuidora del Sur Ltda.", "88.987.654-3", 4, true, "ventas@distribuidorasur.cl", null));
            }
            if (!proveedorRepository.existsById(3)) {
                proveedorRepository.save(new Proveedor(null, "Tecnologías Mundiales SpA", "99.555.444-k", 3, false, "soporte@tecmundiales.com", null));
            }
            if (!proveedorRepository.existsById(4)) {
                proveedorRepository.save(new Proveedor(null, "Batucos Inversiones", "67.522.344-k", 4, false, "batuco@inv.com", null));
            }
            if (!proveedorRepository.existsById(5)) {
                proveedorRepository.save(new Proveedor(null, "Walmart", "78.522.994-1", 5, false, "walmart@empresas.com", null));

            }



        if (!contratoRepository.existsById(1)) {
            Proveedor p1 = proveedorRepository.findById(1).orElse(null);
            contratoRepository.save(new Contrato(null, "CON-2026-001", 1500000.0, LocalDate.of(2026, 1, 15), LocalDate.of(2027, 1, 15), true, p1));
        }
        if (!contratoRepository.existsById(2)) {
            Proveedor p2 = proveedorRepository.findById(2).orElse(null);
            contratoRepository.save(new Contrato(null, "CON-2026-002", 450000.0, LocalDate.of(2026, 3, 1), LocalDate.of(2026, 9, 1), true, p2));
        }
        if (!contratoRepository.existsById(3)) {
            Proveedor p1 = proveedorRepository.findById(1).orElse(null);
            contratoRepository.save(new Contrato(null, "CON-2026-003", 3200000.0, LocalDate.of(2025, 6, 10), LocalDate.of(2026, 6, 10), false, p1));
        }
        if (!contratoRepository.existsById(4)) {
            Proveedor p2 = proveedorRepository.findById(2).orElse(null);
            contratoRepository.save(new Contrato(null, "CON-2026-004", 7200000.0, LocalDate.of(2025, 9, 3), LocalDate.of(2027, 6, 10), false, p2));
        }
        if (!contratoRepository.existsById(5)) {
            Proveedor p1 = proveedorRepository.findById(1).orElse(null);
            contratoRepository.save(new Contrato(null, "CON-2026-005", 4200000.0, LocalDate.of(2026, 4, 11), LocalDate.of(2027, 8, 11), false, p1));
        }

        System.out.println("Datos de Contratos y Proveedores Cargados Correctamente");
    }
}