package com.prueba.ms_inventario.service;

import com.prueba.ms_inventario.cliente.ProductoCliente;
import com.prueba.ms_inventario.dto.InventarioRequestDTO;
import com.prueba.ms_inventario.dto.InventarioResponseDTO;
import com.prueba.ms_inventario.dto.ProductoResponseDTO;
import com.prueba.ms_inventario.exception.ResourceNotFoundException;
import com.prueba.ms_inventario.mapper.InventarioMapper;
import com.prueba.ms_inventario.model.Inventario;
import com.prueba.ms_inventario.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private InventarioMapper inventarioMapper;

    @Mock
    private ProductoCliente productoCliente;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void testCrearInventario_Exitoso() {
        InventarioRequestDTO dto = new InventarioRequestDTO();
        dto.setProductoId(101);

        Inventario inventario = new Inventario();
        inventario.setId(1);
        inventario.setProductoId(101);

        when(productoCliente.obtenerProducto(101)).thenReturn(new ProductoResponseDTO());
        when(inventarioMapper.toEntity(dto)).thenReturn(inventario);
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);
        when(inventarioMapper.toDTO(inventario)).thenReturn(new InventarioResponseDTO());

        InventarioResponseDTO resultado = inventarioService.crear(dto);

        assertNotNull(resultado);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }

    @Test
    void testObtenerPorId_Exitoso() {
        Integer id = 1;
        Inventario inventario = new Inventario();
        inventario.setId(id);
        inventario.setProductoId(101);

        when(inventarioRepository.findById(id)).thenReturn(Optional.of(inventario));
        when(inventarioMapper.toDTO(inventario)).thenReturn(new InventarioResponseDTO());
        when(productoCliente.obtenerProducto(101)).thenReturn(new ProductoResponseDTO());

        InventarioResponseDTO resultado = inventarioService.obtenerPorId(id);

        assertNotNull(resultado);
        verify(productoCliente, times(1)).obtenerProducto(101);
    }

    @Test
    void testObtenerPorId_LanzaException_CuandoNoExiste() {
        Integer id = 99;
        when(inventarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            inventarioService.obtenerPorId(id);
        });
    }

    @Test
    void testCrearInventario_LanzaException_CuandoProductoNoExiste() {
        InventarioRequestDTO dto = new InventarioRequestDTO();
        dto.setProductoId(999);

        when(productoCliente.obtenerProducto(999)).thenThrow(new RuntimeException("Producto no encontrado"));

        assertThrows(ResourceNotFoundException.class, () -> {
            inventarioService.crear(dto);
        });
    }


}