package com.ecommerce.ms_envios.controller;

import com.ecommerce.ms_envios.assemblers.EnvioModelAssembler;
import com.ecommerce.ms_envios.dto.EnvioRequestDTO;
import com.ecommerce.ms_envios.dto.EnvioResponseDTO;
import com.ecommerce.ms_envios.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/envios")
@RequiredArgsConstructor
@Tag(name = "Envíos V2", description = "API HATEOAS para Envíos")
public class EnvioControllerV2 {

    private final EnvioService envioService;
    private final EnvioModelAssembler assembler;

    @Operation(summary = "Listar todos los envios (HATEOAS)")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EnvioResponseDTO>>> listarTodos() {
        List<EntityModel<EnvioResponseDTO>> envios = envioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(envios, linkTo(methodOn(EnvioControllerV2.class).listarTodos()).withSelfRel()));
    }

    @Operation(summary = "Obtener envío por ID (HATEOAS)")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EnvioResponseDTO>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(assembler.toModel(envioService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EnvioResponseDTO> crear(@Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.ok(envioService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}