package com.prueba.ms_pagos.service;


import com.prueba.ms_pagos.cliente.PedidosCliente;
import com.prueba.ms_pagos.dto.PagosRequestDTO;
import com.prueba.ms_pagos.dto.PagosResponseDTO;
import com.prueba.ms_pagos.exception.ResourceNotFoundException;
import com.prueba.ms_pagos.mapper.PagosMapper;
import com.prueba.ms_pagos.model.Pagos;
import com.prueba.ms_pagos.repository.PagosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
@Slf4j

public class PagosService {

    private final PagosRepository pagoRepository;
    private final PagosMapper pagoMapper;
    private final PedidosCliente pedidoCliente;
    private final PedidosCliente pedidosCliente;


    // Lista todos los pagos
    public List<PagosResponseDTO> listarTodos() {
        log.info("Solicitando lista completa de pagos");
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }


    //Obtener pago por id
    public PagosResponseDTO obtenerPorId(Integer id) {
        log.info("Buscando pago con ID: {}", id);
        return pagoRepository.findById(id)
                .map(pagoMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID " + id));
    }


    // Crear pago
    public PagosResponseDTO crear(PagosRequestDTO dto) {
        log.info("Iniciando creación de pago para el pedido ID: {}", dto.getPedidoId());

        Pagos pago = pagoMapper.toEntity(dto);
        Pagos pagoGuardado = pagoRepository.save(pago);
        log.info("Pago registrado con éxito{}", pagoGuardado.getId());

        if ("Aprobado".equalsIgnoreCase(pagoGuardado.getEstado())) {
                log.info("Conectando con ms-pedidos para actualizar estado a pagado...");
                pedidosCliente.actualizarEstadoPedido(pagoGuardado.getPedidoId(), "Pagado");
                log.info("ms-pedidos notificado correctamente.");
        }
        return pagoMapper.toDTO(pagoGuardado);


    }

    // Actualizar Pago

    public PagosResponseDTO actualizar(Integer id, PagosRequestDTO dto) {
        log.info("Iniciando actualización del pago con ID: {}", id);

        Pagos pagoExistente = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));

        pagoExistente.setPedidoId(dto.getPedidoId());
        pagoExistente.setMetodoPago(dto.getMetodoPago());
        pagoExistente.setEstado(dto.getEstado());
        pagoExistente.setMonto(dto.getMonto());

        Pagos pagoActualizado = pagoRepository.save(pagoExistente);
        log.info("Pago con ID: {} actualizado correctamente en DB", id);
        return pagoMapper.toDTO(pagoActualizado);
    }

    //Eliminar pago

    public void eliminar(Integer id) {
        log.info("Intentando eliminar pago con ID: {}", id);
        if (!pagoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar el pago por ID" + id);
        }

        pagoRepository.deleteById(id);
        log.info("El pago con ID {} fue eliminado con éxito", id);
    }




}