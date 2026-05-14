package com.prueba.ms_sucursales.runner;

import com.prueba.ms_sucursales.model.Region;
import com.prueba.ms_sucursales.model.Sucursal;
import com.prueba.ms_sucursales.repository.RegionRepository;
import com.prueba.ms_sucursales.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SucursalRunner implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {

        // 1. Cargar Regiones si la tabla está vacía
        if (regionRepository.count() == 0) {
            Region r1 = new Region(null, "Metropolitana", "RM", "Region del centro", 52, true, LocalDate.now(), null);
            Region r2 = new Region(null, "Valparaíso", "V", "Region costera", 38, true, LocalDate.now(), null);
            Region r3 = new Region(null, "Biobío", "VIII", "Region del sur", 33, true, LocalDate.now(), null);

            regionRepository.saveAll(List.of(r1, r2, r3));
            System.out.println("Runner: Regiones iniciales cargadas");
        }

        // 2. Cargar Sucursales si la tabla está vacía
        if (sucursalRepository.count() == 0) {
            // Buscamos las regiones recien creadas para asociarlas
            Region rm = regionRepository.findAll().get(0);
            Region valpo = regionRepository.findAll().get(1);

            Sucursal s1 = new Sucursal(null, "Sucursal Santiago Centro", "Alameda 123", "+562222333", 100, true, LocalDate.now(), rm);
            Sucursal s2 = new Sucursal(null, "Sucursal Providencia", "Av. Providencia 456", "+562222444", 50, true, LocalDate.now(), rm);
            Sucursal s3 = new Sucursal(null, "Sucursal Viña del Mar", "Libertad 789", "+563222555", 80, true, LocalDate.now(), valpo);

            sucursalRepository.saveAll(List.of(s1, s2, s3));
            System.out.println("Runner: Sucursales iniciales cargadas");
        }
    }
}