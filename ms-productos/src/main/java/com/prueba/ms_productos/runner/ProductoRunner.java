package com.prueba.ms_productos.runner;

import com.prueba.ms_productos.model.Categoria;
import com.prueba.ms_productos.model.Producto;
import com.prueba.ms_productos.repository.CategoriaRepository;
import com.prueba.ms_productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProductoRunner implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!categoriaRepository.existsById(1)) {
            categoriaRepository.save(new Categoria(null, "Electronica", "CAT-01", "Dispositivos y accesorios electronicos", true, LocalDate.now().plusYears(1), null));
        }
        if (!categoriaRepository.existsById(2)) {
            categoriaRepository.save(new Categoria(null, "Hogar", "CAT-02", "Articulos para el hogar y decoracion", true, LocalDate.now().plusYears(1), null));
        }
        if (!categoriaRepository.existsById(3)) {
            categoriaRepository.save(new Categoria(null, "Oficina", "CAT-03", "Articulos de escritorio y papeleria", true, LocalDate.now().plusYears(1), null));
        }

        if (!productoRepository.existsById(1)) {
            Categoria cat1 = categoriaRepository.findById(1).orElse(null);
            productoRepository.save(new Producto(null, "Televisor Smart TV 55", "PROD-001", 450000.0, 15, true, LocalDate.now(), cat1));
        }
        if (!productoRepository.existsById(2)) {
            Categoria cat2 = categoriaRepository.findById(2).orElse(null);
            productoRepository.save(new Producto(null, "Lampara de Mesa LED", "PROD-002", 18000.0, 30, true, LocalDate.now(), cat2));
        }
        if (!productoRepository.existsById(3)) {
            Categoria cat3 = categoriaRepository.findById(3).orElse(null);
            productoRepository.save(new Producto(null, "Silla Ergonomica", "PROD-003", 85000.0, 10, true, LocalDate.now(), cat3));
        }

        System.out.println("Datos de Categorias y Productos Cargados Correctamente");
    }
}