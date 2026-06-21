package com.prueba.ms_inventario.service;

import com.prueba.ms_inventario.dto.MovimientoStockRequestDTO;
import com.prueba.ms_inventario.mapper.MovimientoStockMapper;
import com.prueba.ms_inventario.model.Inventario;
import com.prueba.ms_inventario.model.MovimientoStock;
import com.prueba.ms_inventario.repository.InventarioRepository;
import com.prueba.ms_inventario.repository.MovimientoStockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovimientoStockServiceTest {

    @Mock
    private MovimientoStockRepository repository;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private MovimientoStockMapper mapper;

    @InjectMocks
    private MovimientoStockService service;

    @Test
    void testCrearMovimientoEntrada_Exitoso() {
        Inventario inventario = new Inventario();
        inventario.setId(1);
        inventario.setCantidadDisponible(10);

        MovimientoStockRequestDTO dto = new MovimientoStockRequestDTO();
        dto.setInventarioId(1);
        dto.setTipoMovimiento("ENTRADA");
        dto.setCantidadMoviendo(5);

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));
        when(mapper.toEntity(any())).thenReturn(new MovimientoStock());
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        service.crear(dto);

        assertEquals(15, inventario.getCantidadDisponible());
        verify(inventarioRepository, times(1)).save(inventario);
    }

    @Test
    void testCrearMovimientoSalida_Exitoso() {
        Inventario inventario = new Inventario();
        inventario.setId(1);
        inventario.setCantidadDisponible(20);

        MovimientoStockRequestDTO dto = new MovimientoStockRequestDTO();
        dto.setInventarioId(1);
        dto.setTipoMovimiento("SALIDA");
        dto.setCantidadMoviendo(5);

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));
        when(mapper.toEntity(any())).thenReturn(new MovimientoStock());
        when(repository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        service.crear(dto);

        assertEquals(15, inventario.getCantidadDisponible());
        verify(inventarioRepository, times(1)).save(inventario);
    }

    @Test
    void testCrearMovimientoSalida_LanzaException_CuandoStockInsuficiente() {
        // GIVEN
        Inventario inventario = new Inventario();
        inventario.setId(1);
        inventario.setCantidadDisponible(2);

        MovimientoStockRequestDTO dto = new MovimientoStockRequestDTO();
        dto.setInventarioId(1);
        dto.setTipoMovimiento("SALIDA");
        dto.setCantidadMoviendo(5);

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));

        assertThrows(IllegalArgumentException.class, () -> {
            service.crear(dto);
        });
    }
}