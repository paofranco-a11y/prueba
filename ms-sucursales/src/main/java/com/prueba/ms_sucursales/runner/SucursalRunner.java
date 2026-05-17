package com.prueba.ms_sucursales.runner;

import com.prueba.ms_sucursales.model.Region;
import com.prueba.ms_sucursales.model.Sucursal;
import com.prueba.ms_sucursales.repository.RegionRepository;
import com.prueba.ms_sucursales.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SucursalRunner implements CommandLineRunner {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!regionRepository.existsById(1)) {
            regionRepository.save(new Region(null, "Metropolitana", "RM", "Region del centro", 52, true, LocalDate.now(), null));
        }
        if (!regionRepository.existsById(2)) {
            regionRepository.save(new Region(null, "Valparaíso", "V", "Region costera", 38, true, LocalDate.now(), null));
        }
        if (!regionRepository.existsById(3)) {
            regionRepository.save(new Region(null, "Biobío", "VIII", "Region del sur", 33, true, LocalDate.now(), null));
        }

        if (!sucursalRepository.existsById(1)) {
            Region rm = regionRepository.findById(1).orElse(null);
            sucursalRepository.save(new Sucursal(null, "Sucursal Santiago Centro", "Alameda 123", "+562222333", 100, true, LocalDate.now(), rm));
        }
        if (!sucursalRepository.existsById(2)) {
            Region rm = regionRepository.findById(1).orElse(null);
            sucursalRepository.save(new Sucursal(null, "Sucursal Providencia", "Av. Providencia 456", "+562222444", 50, true, LocalDate.now(), rm));
        }
        if (!sucursalRepository.existsById(3)) {
            Region valpo = regionRepository.findById(2).orElse(null);
            sucursalRepository.save(new Sucursal(null, "Sucursal Viña del Mar", "Libertad 789", "+563222555", 80, true, LocalDate.now(), valpo));
        }

        System.out.println("Datos iniciales cargados correctamente");
    }
}