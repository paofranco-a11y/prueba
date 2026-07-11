package com.ecommerce.ms_envios.service;

import com.ecommerce.ms_envios.client.PedidoClient;
import com.ecommerce.ms_envios.client.UsuarioClient;
import com.ecommerce.ms_envios.dto.EnvioRequestDTO;
import com.ecommerce.ms_envios.dto.EnvioResponseDTO;
import com.ecommerce.ms_envios.model.Envio;
import com.ecommerce.ms_envios.repository.EnvioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @Mock
    private PedidoClient pedidoClient;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private EnvioService envioService;

    @Test
    void testFindAll_Exitoso() {
        Envio envio = new Envio(1, 101, "Direccion", 1000f, false, LocalDate.now());
        when(envioRepository.findAll()).thenReturn(Collections.singletonList(envio));

        List<EnvioResponseDTO> resultado = envioService.findAll();
        assertFalse(resultado.isEmpty());
        verify(envioRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Exitoso() {
        Envio envio = new Envio(1, 101, "Direccion", 1000f, false, LocalDate.now());
        when(envioRepository.findById(1)).thenReturn(Optional.of(envio));

        EnvioResponseDTO resultado = envioService.findById(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void testFindById_Fallo_NoExiste() {
        when(envioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> envioService.findById(99));
    }

    @Test
    void testSave_Exitoso() {
        EnvioRequestDTO request = new EnvioRequestDTO();
        request.setPedidoId(101);
        Envio envio = new Envio(1, 101, "Direccion", 1000f, false, LocalDate.now());

        // Simulamos que Feign responde bien
        when(pedidoClient.obtenerPedidoPorId(101)).thenReturn(null);
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);

        EnvioResponseDTO resultado = envioService.save(request);
        assertNotNull(resultado);
        verify(envioRepository, times(1)).save(any(Envio.class));
    }

    @Test
    void testSave_Fallo_FeignError() {
        EnvioRequestDTO request = new EnvioRequestDTO();
        request.setPedidoId(101);

        // Simulamos que Feign lanza error
        when(pedidoClient.obtenerPedidoPorId(101)).thenThrow(new RuntimeException("Pedido no existe"));

        assertThrows(RuntimeException.class, () -> envioService.save(request));
        verify(envioRepository, never()).save(any(Envio.class));
    }

    @Test
    void testUpdate_Exitoso() {
        EnvioRequestDTO request = new EnvioRequestDTO();
        request.setDireccionDestino("Nueva Direccion");
        Envio existente = new Envio(1, 101, "Vieja Direccion", 1000f, false, LocalDate.now());

        when(envioRepository.findById(1)).thenReturn(Optional.of(existente));
        when(envioRepository.save(any(Envio.class))).thenReturn(existente);

        EnvioResponseDTO resultado = envioService.update(1, request);
        assertNotNull(resultado);
    }

    @Test
    void testDelete_Exitoso() {
        Envio existente = new Envio(1, 101, "Vieja Direccion", 1000f, false, LocalDate.now());
        when(envioRepository.findById(1)).thenReturn(Optional.of(existente));
        doNothing().when(envioRepository).delete(existente);

        assertDoesNotThrow(() -> envioService.delete(1));
        verify(envioRepository, times(1)).delete(existente);
    }
}