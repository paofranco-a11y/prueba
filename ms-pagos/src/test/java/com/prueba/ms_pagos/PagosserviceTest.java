package com.prueba.ms_pagos;

import com.prueba.ms_pagos.cliente.PedidosCliente;
import com.prueba.ms_pagos.dto.PagosRequestDTO;
import com.prueba.ms_pagos.dto.PagosResponseDTO;
import com.prueba.ms_pagos.exception.ResourceNotFoundException;
import com.prueba.ms_pagos.mapper.PagosMapper;
import com.prueba.ms_pagos.model.Pagos;
import com.prueba.ms_pagos.repository.PagosRepository;
import com.prueba.ms_pagos.service.PagosService;
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
class PagosServiceTest {

    @Mock
    private PagosRepository pagoRepository;

    @Mock
    private PagosMapper pagoMapper;

    @Mock
    private PedidosCliente pedidosCliente;

    @InjectMocks
    private PagosService pagosService;

    // TEST 1: Listar todos los pagos
    @Test
    void deberiaRetornarListaDePagos() {
        Pagos pago = new Pagos();
        pago.setId(1);
        PagosResponseDTO dto = new PagosResponseDTO();

        when(pagoRepository.findAll()).thenReturn(List.of(pago));
        when(pagoMapper.toDTO(pago)).thenReturn(dto);

        List<PagosResponseDTO> resultado = pagosService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(pagoRepository, times(1)).findAll();
    }

    // TEST 2: Obtener pago por ID existente
    @Test
    void deberiaRetornarPagoPorId() {
        Pagos pago = new Pagos();
        pago.setId(1);
        PagosResponseDTO dto = new PagosResponseDTO();

        when(pagoRepository.findById(1)).thenReturn(Optional.of(pago));
        when(pagoMapper.toDTO(pago)).thenReturn(dto);

        PagosResponseDTO resultado = pagosService.obtenerPorId(1);

        assertNotNull(resultado);
        verify(pagoRepository, times(1)).findById(1);
    }

    // TEST 3: Obtener pago por ID inexistente
    @Test
    void deberiaLanzarExcepcionCuandoPagoNoExiste() {
        when(pagoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            pagosService.obtenerPorId(99);
        });
    }

    // TEST 4: Eliminar pago existente
    @Test
    void deberiaEliminarPagoExistente() {
        when(pagoRepository.existsById(1)).thenReturn(true);
        doNothing().when(pagoRepository).deleteById(1);

        pagosService.eliminar(1);

        verify(pagoRepository, times(1)).deleteById(1);
    }

    // TEST 5: Eliminar pago inexistente lanza excepcion
    @Test
    void deberiaLanzarExcepcionAlEliminarPagoInexistente() {
        when(pagoRepository.existsById(99)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            pagosService.eliminar(99);
        });
    }
}