package com.prueba.ms_proveedores.service;

import com.prueba.ms_proveedores.dto.ProveedorDTO;
import com.prueba.ms_proveedores.dto.ProveedorRequestDTO;
import com.prueba.ms_proveedores.exception.ResourceNotFoundException;
import com.prueba.ms_proveedores.mapper.ProveedorMapper;
import com.prueba.ms_proveedores.model.Proveedor;
import com.prueba.ms_proveedores.repository.ProveedorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private ProveedorMapper proveedorMapper;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void testListarTodos_Exitoso() {
        Proveedor proveedor = new Proveedor();
        when(proveedorRepository.findAll()).thenReturn(Collections.singletonList(proveedor));
        when(proveedorMapper.toDTO(proveedor)).thenReturn(new ProveedorDTO());

        List<ProveedorDTO> resultado = proveedorService.listarTodos();
        assertFalse(resultado.isEmpty());
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Exitoso() {
        Proveedor proveedor = new Proveedor();
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedor));
        when(proveedorMapper.toDTO(proveedor)).thenReturn(new ProveedorDTO());

        ProveedorDTO resultado = proveedorService.buscarPorId(1);
        assertNotNull(resultado);
    }

    @Test
    void testBuscarPorId_Fallo_LanzaResourceNotFoundException() {
        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> proveedorService.buscarPorId(99));
    }

    @Test
    void testCrear_Exitoso() {
        ProveedorRequestDTO req = new ProveedorRequestDTO();
        Proveedor proveedor = new Proveedor();
        when(proveedorMapper.toEntity(req)).thenReturn(proveedor);
        when(proveedorRepository.save(proveedor)).thenReturn(proveedor);
        when(proveedorMapper.toDTO(proveedor)).thenReturn(new ProveedorDTO());

        ProveedorDTO resultado = proveedorService.crear(req);
        assertNotNull(resultado);
    }

    @Test
    void testActualizar_Exitoso() {
        ProveedorRequestDTO req = new ProveedorRequestDTO();
        Proveedor existente = new Proveedor();
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(existente));
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(existente);
        when(proveedorMapper.toDTO(existente)).thenReturn(new ProveedorDTO());

        ProveedorDTO resultado = proveedorService.actualizar(1, req);
        assertNotNull(resultado);
    }

    @Test
    void testActualizar_Fallo_LanzaException() {
        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> proveedorService.actualizar(99, new ProveedorRequestDTO()));
    }

    @Test
    void testEliminar_Exitoso() {
        when(proveedorRepository.existsById(1)).thenReturn(true);
        doNothing().when(proveedorRepository).deleteById(1);

        assertDoesNotThrow(() -> proveedorService.eliminar(1));
    }

    @Test
    void testEliminar_Fallo_LanzaException() {
        when(proveedorRepository.existsById(99)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> proveedorService.eliminar(99));
    }
}