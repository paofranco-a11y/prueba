package com.prueba.ms_productos.service;

import com.prueba.ms_productos.dto.ProductoDTO;
import com.prueba.ms_productos.dto.ProductoRequestDTO;
import com.prueba.ms_productos.exception.ResourceNotFoundException;
import com.prueba.ms_productos.mapper.ProductoMapper;
import com.prueba.ms_productos.model.Categoria;
import com.prueba.ms_productos.model.Producto;
import com.prueba.ms_productos.repository.CategoriaRepository;
import com.prueba.ms_productos.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public List<ProductoDTO> listarProductos() {
        log.info("Consultando todos los registros de productos");
        try {
            return productoRepository.findAll().stream()
                    .map(productoMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar productos: {}", e.getMessage());
            throw e;
        }
    }

    public ProductoDTO obtenerProductoPorId(Integer id) {
        log.info("Buscando producto con ID: {}", id);
        try {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
            return productoMapper.toDTO(producto);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar producto por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public ProductoDTO crearProducto(ProductoRequestDTO dto) {
        log.info("Creando un nuevo producto");
        try {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + dto.getCategoriaId()));

            Producto producto = productoMapper.toEntity(dto, categoria);
            return productoMapper.toDTO(productoRepository.save(producto));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al crear producto: {}", e.getMessage());
            throw e;
        }
    }

    public ProductoDTO actualizarProducto(Integer id, ProductoRequestDTO dto) {
        log.info("Actualizando producto con ID: {}", id);
        try {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + dto.getCategoriaId()));

            producto.setNombre(dto.getNombre());
            producto.setCodigo(dto.getCodigo());
            producto.setPrecio(dto.getPrecio());
            producto.setStock(dto.getStock());
            producto.setActivo(dto.isActivo());
            producto.setFechaRegistro(dto.getFechaRegistro());
            producto.setCategoria(categoria);

            return productoMapper.toDTO(productoRepository.save(producto));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar producto por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarProducto(Integer id) {
        log.info("Eliminando producto con ID: {}", id);
        try {
            Producto producto = productoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontro el producto para eliminar con ID: " + id));
            productoRepository.delete(producto);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar producto por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public List<ProductoDTO> buscarPorNombreYPrecioMenor(String nombre, Double precioMaximo) {
        log.info("Buscando productos cuyo nombre contenga '{}' y precio menor a {}", nombre, precioMaximo);
        try {
            return productoRepository.findByNombreContainingIgnoreCaseAndPrecioLessThan(nombre, precioMaximo).stream()
                    .map(productoMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al buscar productos por nombre y precio maximo: {}", e.getMessage());
            throw e;
        }
    }


    // ---- MÉTODOS HATEOAS (V2) ----

    public List<Producto> listarProductosModel() {
        log.info("HATEOAS - Listando todos los productos");
        return productoRepository.findAll();
    }

    public Producto obtenerProductoModelPorId(Integer id) {
        log.info("HATEOAS - Buscando producto con ID: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    public Producto crearProductoModel(Producto producto) {
        log.info("HATEOAS - Creando producto");
        return productoRepository.save(producto);
    }

    public Producto actualizarProductoModel(Integer id, Producto producto) {
        log.info("HATEOAS - Actualizando producto con ID: {}", id);
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        existente.setNombre(producto.getNombre());
        existente.setCodigo(producto.getCodigo());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());
        existente.setActivo(producto.isActivo());
        existente.setFechaRegistro(producto.getFechaRegistro());
        existente.setCategoria(producto.getCategoria());

        return productoRepository.save(existente);
    }

    public List<Producto> buscarPorNombreYPrecioMenorModel(String nombre, Double precioMaximo) {
        log.info("HATEOAS - Buscando productos por nombre '{}' y precio menor a {}", nombre, precioMaximo);
        return productoRepository.findByNombreContainingIgnoreCaseAndPrecioLessThan(nombre, precioMaximo);
    }
}