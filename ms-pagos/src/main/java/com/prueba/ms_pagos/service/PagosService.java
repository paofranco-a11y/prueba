package com.prueba.ms_pagos.service;


import com.prueba.ms_pagos.cliente.PedidosCliente;
import com.prueba.ms_pagos.dto.PagosRequestDTO;
import com.prueba.ms_pagos.dto.PagosResponseDTO;
import com.prueba.ms_pagos.dto.PedidoResponseDTO;
import com.prueba.ms_pagos.exception.ResourceNotFoundException;
import com.prueba.ms_pagos.mapper.PagosMapper;
import com.prueba.ms_pagos.model.Pagos;
import com.prueba.ms_pagos.repository.PagosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagosService {

    private final PagosRepository pagoRepository;
    private final PagosMapper pagoMapper;
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
        log.info("Buscando pago con ID {}", id);
        Pagos pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID " + id));
        PagosResponseDTO responseDTO = pagoMapper.toDTO(pago);
        try {
            PedidoResponseDTO pedidoDatos = pedidosCliente.obtenerPedido(pago.getPedidoId());
            responseDTO.setPedido(pedidoDatos);
            log.info("Informacion del pedido cargada exitosamente al pago.");
        } catch (Exception e) {
            log.warn("No se pudo cargar la informacion completa del pedido ID {}", pago.getPedidoId());
        }
        return responseDTO;
    }

    // Crear pago
    public PagosResponseDTO crear(PagosRequestDTO dto) {
        log.info("Iniciando creacion de pago para el pedido ID: {}", dto.getPedidoId());
        try {
            pedidosCliente.obtenerPedido(dto.getPedidoId());
            log.info("Pedido validado correctamente");
        } catch (Exception e) {
            log.error("Error: intento de pago para un pedido no registrado.");
            throw new ResourceNotFoundException("No se puede registrar el pago, el pedido con ID " + dto.getPedidoId() + " no existe.");
        }

        PedidoResponseDTO pedidoObtenido;
        try {
            pedidoObtenido = pedidosCliente.obtenerPedido(dto.getPedidoId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("El pedido no existe");
        }
        dto.setMonto(pedidoObtenido.getTotal());

        Pagos pago = pagoMapper.toEntity(dto);
        Pagos pagoGuardado = pagoRepository.save(pago);
        log.info("Pago registrado con exito ID: {}", pagoGuardado.getId());

        if ("Aprobado".equalsIgnoreCase(pagoGuardado.getEstado())) {
            try {
                log.info("Actualizando estado del pago");
                pedidosCliente.actualizarEstadoPedido(pagoGuardado.getPedidoId(), "Pagado");
                log.info("ms-pedidos notificado correctamente.");
            } catch (Exception e) {
                log.error("Error, el pago se guardo pero no se pudo validar {}", e.getMessage());
            }
        }
        return pagoMapper.toDTO(pagoGuardado);
    }

    // Actualizar Pago
    public PagosResponseDTO actualizar(Integer id, PagosRequestDTO dto) {
        log.info("Iniciando actualizacion del pago con ID: {}", id);
        Pagos pagoExistente = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
        try {
            pedidosCliente.obtenerPedido(dto.getPedidoId());
            log.info("Nuevo pedido validado correctamente");
        } catch (Exception e) {
            log.error("Error: intento de actualizacion hacia un pedido inexistente.");
            throw new ResourceNotFoundException("No se puede actualizar el pago, el pedido con ID " + dto.getPedidoId() + " no existe");
        }

        pagoExistente.setPedidoId(dto.getPedidoId());
        pagoExistente.setMetodoPago(dto.getMetodoPago());
        pagoExistente.setEstado(dto.getEstado());
        pagoExistente.setMonto(dto.getMonto());

        Pagos pagoActualizado = pagoRepository.save(pagoExistente);
        log.info("Pago con ID {} actualizado correctamente en DB", id);

        if ("Aprobado".equalsIgnoreCase(pagoActualizado.getEstado())) {
            try {
                log.info("Conectando con ms-pedidos tras cambio de estado");
                pedidosCliente.actualizarEstadoPedido(pagoActualizado.getPedidoId(), "Pagado");
                log.info("ms-pedidos notificado con exito tras actualizacion");
            } catch (Exception e) {
                log.error("No se pudo notificar el cambio de estado del pago por fallo {}", e.getMessage());
            }
        }
        return pagoMapper.toDTO(pagoActualizado);
    }

    // Eliminar pago
    public void eliminar(Integer id) {
        log.info("Intentando eliminar pago con ID: {}", id);
        if (!pagoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar el pago por ID" + id);
        }
        pagoRepository.deleteById(id);
        log.info("El pago con ID {} fue eliminado con exito", id);
    }


    // ---- MÉTODOS HATEOAS (V2) ----

    // Lista todos los pagos como entidad (para HATEOAS)
    public List<Pagos> listarTodosModel() {
        log.info("HATEOAS - Solicitando lista completa de pagos");
        return pagoRepository.findAll();
    }

    // Obtener pago por ID como entidad (para HATEOAS)
    public Pagos obtenerModelPorId(Integer id) {
        log.info("HATEOAS - Buscando pago con ID {}", id);
        return pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID " + id));
    }

    // Crear pago desde entidad (para HATEOAS)
    public Pagos crearModel(Pagos pago) {
        log.info("HATEOAS - Creando pago para pedido ID: {}", pago.getPedidoId());
        return pagoRepository.save(pago);
    }

    // Actualizar pago desde entidad (para HATEOAS)
    public Pagos actualizarModel(Integer id, Pagos pago) {
        log.info("HATEOAS - Actualizando pago con ID: {}", id);
        Pagos existente = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));

        existente.setPedidoId(pago.getPedidoId());
        existente.setMetodoPago(pago.getMetodoPago());
        existente.setEstado(pago.getEstado());
        existente.setMonto(pago.getMonto());

        return pagoRepository.save(existente);
    }
}