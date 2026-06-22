package com.prueba.ms_pedidos;

import com.prueba.ms_pedidos.cliente.ProductoCliente;
import com.prueba.ms_pedidos.cliente.UsuarioCliente;
import com.prueba.ms_pedidos.dto.PedidoResponseDTO;
import com.prueba.ms_pedidos.exception.ResourceNotFoundException;
import com.prueba.ms_pedidos.mapper.PedidoMapper;
import com.prueba.ms_pedidos.model.Pedido;
import com.prueba.ms_pedidos.repository.PedidoRepository;
import com.prueba.ms_pedidos.service.PedidoService;
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
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private PedidoMapper mapper;

    @Mock
    private UsuarioCliente usuarioCliente;

    @Mock
    private ProductoCliente productoCliente;

    @InjectMocks
    private PedidoService pedidoService;

    // TEST 1: Listar todos los pedidos
    @Test
    void deberiaRetornarListaDePedidos() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        PedidoResponseDTO dto = new PedidoResponseDTO();

        when(repository.findAll()).thenReturn(List.of(pedido));
        when(mapper.toDTO(pedido)).thenReturn(dto);

        List<PedidoResponseDTO> resultado = pedidoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    // TEST 2: Obtener pedido por ID existente
    @Test
    void deberiaRetornarPedidoPorId() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        PedidoResponseDTO dto = new PedidoResponseDTO();

        when(repository.findById(1)).thenReturn(Optional.of(pedido));
        when(mapper.toDTO(pedido)).thenReturn(dto);

        PedidoResponseDTO resultado = pedidoService.obtenerPorId(1);

        assertNotNull(resultado);
        verify(repository, times(1)).findById(1);
    }

    // TEST 3: Obtener pedido por ID inexistente
    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.obtenerPorId(99);
        });
    }

    // TEST 4: Eliminar pedido existente
    @Test
    void deberiaEliminarPedidoExistente() {
        Pedido pedido = new Pedido();
        pedido.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(pedido));
        doNothing().when(repository).delete(pedido);

        pedidoService.eliminar(1);

        verify(repository, times(1)).delete(pedido);
    }

    // TEST 5: Eliminar pedido inexistente lanza excepcion
    @Test
    void deberiaLanzarExcepcionAlEliminarPedidoInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            pedidoService.eliminar(99);
        });
    }

    // TEST 6: Actualizar estado a PAGADO
    @Test
    void deberiaActualizarEstadoPedidoAPagado() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setPagado(false);

        when(repository.findById(1)).thenReturn(Optional.of(pedido));
        when(repository.save(pedido)).thenReturn(pedido);

        pedidoService.actualizarEstado(1, "PAGADO");

        assertTrue(pedido.getPagado());
        verify(repository, times(1)).save(pedido);
    }

    // TEST 7: Actualizar estado a no pagado
    @Test
    void deberiaActualizarEstadoPedidoANoPagado() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setPagado(true);

        when(repository.findById(1)).thenReturn(Optional.of(pedido));
        when(repository.save(pedido)).thenReturn(pedido);

        pedidoService.actualizarEstado(1, "PENDIENTE");

        assertFalse(pedido.getPagado());
        verify(repository, times(1)).save(pedido);
    }
}