package com.prueba.ms_pedidos.controller;

import com.prueba.ms_pedidos.dto.DetallePedidoRequestDTO;
import com.prueba.ms_pedidos.dto.DetallePedidoResponseDTO;
import com.prueba.ms_pedidos.service.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Habilita esta clase como un controlador para gestionar las solicitudes HTTP de los detalles de pedidos
@RestController
// Define la ruta base para todos los endpoints de este controlador
@RequestMapping("/api/v1/detalles-pedido")
@Slf4j
public class DetallePedidoController {
    // Inyecta de forma automatica la capa de negocio correspondiente a los detalles del pedido
    @Autowired
    private DetallePedidoService service;

    // Define el endpoint GET para recuperar la lista completa de detalles registrados
    @GetMapping
    public ResponseEntity<List<DetallePedidoResponseDTO>> obtenerTodos() {
        log.info("/api/v1/detalles-pedido - Solicitando listado completo");
        return ResponseEntity.ok(service.obtenerTodos());
    }
    // Define el endpoint GET para buscar un detalle especifico por medio de su ID
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("/api/v1/detalles-pedido/{} - Buscando registro", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
    // Define el endpoint POST para dar de alta un nuevo item validando los datos de entrada
    @PostMapping
    public ResponseEntity<DetallePedidoResponseDTO> crear(@Valid @RequestBody DetallePedidoRequestDTO dto) {
        log.info("/api/v1/detalles-pedido - Agregando nuevo item");
        return new ResponseEntity<>(service.crear(dto), HttpStatus.CREATED);
    }
    // Define el endpoint PUT para modificar la informacion de un detalle existente a partir de su ID
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DetallePedidoRequestDTO dto) {
        log.info("/api/v1/detalles-pedido/{} - Solicitud de edicion", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }
    // Define el endpoint DELETE  eliminar fisicamente un detalle mediante su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("/api/v1/detalles-pedido/{} - Solicitud de eliminacion", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}