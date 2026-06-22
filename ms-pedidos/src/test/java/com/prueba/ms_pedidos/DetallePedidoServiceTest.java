package com.prueba.ms_pedidos;

import com.prueba.ms_pedidos.dto.DetallePedidoResponseDTO;
import com.prueba.ms_pedidos.exception.ResourceNotFoundException;
import com.prueba.ms_pedidos.mapper.DetallePedidoMapper;
import com.prueba.ms_pedidos.model.DetallePedido;
import com.prueba.ms_pedidos.model.Pedido;
import com.prueba.ms_pedidos.repository.DetallePedidoRepository;
import com.prueba.ms_pedidos.repository.PedidoRepository;
import com.prueba.ms_pedidos.service.DetallePedidoService;
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
class DetallePedidoServiceTest {

    @Mock
    private DetallePedidoRepository repository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private DetallePedidoMapper mapper;

    @InjectMocks
    private DetallePedidoService detallePedidoService;

    // TEST 1: Listar todos los detalles
    @Test
    void deberiaRetornarListaDeDetalles() {
        DetallePedido detalle = new DetallePedido();
        detalle.setId(1);
        DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();

        when(repository.findAll()).thenReturn(List.of(detalle));
        when(mapper.toDTO(detalle)).thenReturn(dto);

        List<DetallePedidoResponseDTO> resultado = detallePedidoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // TEST 2: Obtener detalle por ID existente
    @Test
    void deberiaRetornarDetallePorId() {
        DetallePedido detalle = new DetallePedido();
        detalle.setId(1);
        DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();

        when(repository.findById(1)).thenReturn(Optional.of(detalle));
        when(mapper.toDTO(detalle)).thenReturn(dto);

        DetallePedidoResponseDTO resultado = detallePedidoService.obtenerPorId(1);

        assertNotNull(resultado);
        verify(repository, times(1)).findById(1);
    }

    // TEST 3: Obtener detalle por ID inexistente
    @Test
    void deberiaLanzarExcepcionCuandoDetalleNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            detallePedidoService.obtenerPorId(99);
        });
    }

    // TEST 4: Eliminar detalle existente
    @Test
    void deberiaEliminarDetalleExistente() {
        DetallePedido detalle = new DetallePedido();
        detalle.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(detalle));
        doNothing().when(repository).delete(detalle);

        detallePedidoService.eliminar(1);

        verify(repository, times(1)).delete(detalle);
    }

    // TEST 5: Eliminar detalle inexistente lanza excepcion
    @Test
    void deberiaLanzarExcepcionAlEliminarDetalleInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            detallePedidoService.eliminar(99);
        });
    }

    // TEST 6: Crear detalle con pedido inexistente lanza excepcion
    @Test
    void deberiaLanzarExcepcionAlCrearDetalleConPedidoInexistente() {
        com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO dto =
                new com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO();
        dto.setPedidoId(99);

        when(pedidoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            detallePedidoService.crear(dto);
        });
    }

    // TEST 7: Crear detalle con pedido existente
    @Test
    void deberiaCrearDetalleConPedidoExistente() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        DetallePedido detalle = new DetallePedido();
        DetallePedidoResponseDTO dto = new DetallePedidoResponseDTO();

        com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO requestDTO =
                new com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO();
        requestDTO.setPedidoId(1);

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));
        when(mapper.toEntity(requestDTO)).thenReturn(detalle);
        when(repository.save(detalle)).thenReturn(detalle);
        when(mapper.toDTO(detalle)).thenReturn(dto);

        DetallePedidoResponseDTO resultado = detallePedidoService.crear(requestDTO);

        assertNotNull(resultado);
        verify(repository, times(1)).save(detalle);
    }
}