package com.prueba.ms_proveedores.service;

import com.prueba.ms_proveedores.dto.ContratoDTO;
import com.prueba.ms_proveedores.dto.ContratoRequestDTO;
import com.prueba.ms_proveedores.exception.ResourceNotFoundException;
import com.prueba.ms_proveedores.mapper.ContratoMapper;
import com.prueba.ms_proveedores.model.Contrato;
import com.prueba.ms_proveedores.model.Proveedor;
import com.prueba.ms_proveedores.repository.ContratoRepository;
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
public class ContratoServiceTest {

    @Mock
    private ContratoRepository contratoRepository;

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private ContratoMapper contratoMapper;

    @InjectMocks
    private ContratoService contratoService;

    // TESTS PARA LISTAR TODOS
    @Test
    void testListarTodos_Exitoso() {
        Contrato contrato = new Contrato();
        when(contratoRepository.findAll()).thenReturn(Collections.singletonList(contrato));
        when(contratoMapper.toDTO(contrato)).thenReturn(new ContratoDTO());

        List<ContratoDTO> resultado = contratoService.listarTodos();

        assertFalse(resultado.isEmpty());
        verify(contratoRepository, times(1)).findAll();
    }

    //  TESTS PARA BUSCAR POR ID
    @Test
    void testBuscarPorId_Exitoso() {
        Contrato contrato = new Contrato();
        when(contratoRepository.findById(1)).thenReturn(Optional.of(contrato));
        when(contratoMapper.toDTO(contrato)).thenReturn(new ContratoDTO());

        ContratoDTO resultado = contratoService.buscarPorId(1);

        assertNotNull(resultado);
        verify(contratoRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorId_Fallo_LanzaResourceNotFoundException() {
        when(contratoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contratoService.buscarPorId(99);
        });
    }

    //  TESTS PARA CREAR CONTRATO
    @Test
    void testCrear_Exitoso() {
        ContratoRequestDTO request = new ContratoRequestDTO();
        request.setProveedorId(1);

        Proveedor proveedor = new Proveedor();
        Contrato contrato = new Contrato();

        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedor));
        when(contratoMapper.toEntity(request, proveedor)).thenReturn(contrato);
        when(contratoRepository.save(contrato)).thenReturn(contrato);
        when(contratoMapper.toDTO(contrato)).thenReturn(new ContratoDTO());

        ContratoDTO resultado = contratoService.crear(request);

        assertNotNull(resultado);
        verify(contratoRepository, times(1)).save(contrato);
    }

    @Test
    void testCrear_Fallo_ProveedorNoExiste() {
        ContratoRequestDTO request = new ContratoRequestDTO();
        request.setProveedorId(99);

        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contratoService.crear(request);
        });
        verify(contratoRepository, never()).save(any(Contrato.class));
    }

    //  TESTS PARA ACTUALIZAR CONTRATO
    @Test
    void testActualizar_Exitoso() {
        ContratoRequestDTO request = new ContratoRequestDTO();
        request.setProveedorId(1);

        Contrato existente = new Contrato();
        Proveedor proveedor = new Proveedor();

        when(contratoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedor));
        when(contratoRepository.save(any(Contrato.class))).thenReturn(existente);
        when(contratoMapper.toDTO(existente)).thenReturn(new ContratoDTO());

        ContratoDTO resultado = contratoService.actualizar(1, request);

        assertNotNull(resultado);
        verify(contratoRepository, times(1)).save(any(Contrato.class));
    }

    @Test
    void testActualizar_Fallo_ContratoNoExiste() {
        ContratoRequestDTO request = new ContratoRequestDTO();
        when(contratoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            contratoService.actualizar(99, request);
        });
    }

    //  TESTS PARA ELIMINAR CONTRATO
    @Test
    void testEliminar_Exitoso() {
        when(contratoRepository.existsById(1)).thenReturn(true);
        doNothing().when(contratoRepository).deleteById(1);

        assertDoesNotThrow(() -> {
            contratoService.eliminar(1);
        });
        verify(contratoRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminar_Fallo_NoExiste() {
        when(contratoRepository.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            contratoService.eliminar(99);
        });
        verify(contratoRepository, never()).deleteById(anyInt());
    }
}