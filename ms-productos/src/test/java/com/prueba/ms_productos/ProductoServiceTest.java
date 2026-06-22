package com.prueba.ms_productos;

import com.prueba.ms_productos.dto.ProductoDTO;
import com.prueba.ms_productos.exception.ResourceNotFoundException;
import com.prueba.ms_productos.mapper.ProductoMapper;
import com.prueba.ms_productos.model.Categoria;
import com.prueba.ms_productos.model.Producto;
import com.prueba.ms_productos.repository.CategoriaRepository;
import com.prueba.ms_productos.repository.ProductoRepository;
import com.prueba.ms_productos.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoService productoService;

    // TEST 1: Listar todos los productos
    @Test
    void deberiaRetornarListaDeProductos() {
        Producto producto = new Producto();
        producto.setId(1);
        ProductoDTO dto = new ProductoDTO();

        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoMapper.toDTO(producto)).thenReturn(dto);

        List<ProductoDTO> resultado = productoService.listarProductos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(productoRepository, times(1)).findAll();
    }

    // TEST 2: Obtener producto por ID existente
    @Test
    void deberiaRetornarProductoPorId() {
        Producto producto = new Producto();
        producto.setId(1);
        ProductoDTO dto = new ProductoDTO();

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        when(productoMapper.toDTO(producto)).thenReturn(dto);

        ProductoDTO resultado = productoService.obtenerProductoPorId(1);

        assertNotNull(resultado);
        verify(productoRepository, times(1)).findById(1);
    }

    // TEST 3: Obtener producto por ID inexistente
    @Test
    void deberiaLanzarExcepcionCuandoProductoNoExiste() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productoService.obtenerProductoPorId(99);
        });
    }

    // TEST 4: Eliminar producto existente
    @Test
    void deberiaEliminarProductoExistente() {
        Producto producto = new Producto();
        producto.setId(1);

        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));
        doNothing().when(productoRepository).delete(producto);

        productoService.eliminarProducto(1);

        verify(productoRepository, times(1)).delete(producto);
    }

    // TEST 5: Eliminar producto inexistente lanza excepcion
    @Test
    void deberiaLanzarExcepcionAlEliminarProductoInexistente() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productoService.eliminarProducto(99);
        });
    }

    // TEST 6: Buscar por nombre y precio
    @Test
    void deberiaRetornarProductosFiltradosPorNombreYPrecio() {
        Producto producto = new Producto();
        ProductoDTO dto = new ProductoDTO();

        when(productoRepository.findByNombreContainingIgnoreCaseAndPrecioLessThan("laptop", 1000.0))
                .thenReturn(List.of(producto));
        when(productoMapper.toDTO(producto)).thenReturn(dto);

        List<ProductoDTO> resultado = productoService.buscarPorNombreYPrecioMenor("laptop", 1000.0);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    // TEST 7: Crear producto con categoria inexistente lanza excepcion
    @Test
    void deberiaLanzarExcepcionAlCrearProductoConCategoriaInexistente() {
        com.prueba.ms_productos.dto.ProductoRequestDTO dto = new com.prueba.ms_productos.dto.ProductoRequestDTO();
        dto.setCategoriaId(99);

        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            productoService.crearProducto(dto);
        });
    }
}