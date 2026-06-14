package com.ecommerce.msempleados.controller;

import com.ecommerce.msempleados.dto.EmpleadoDTO;
import com.ecommerce.msempleados.dto.EmpleadoRequestDTO;
import com.ecommerce.msempleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public ResponseEntity<List<EmpleadoDTO>> listarTodos() {
        log.info("Controller: Listando todos los empleados");
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("Controller: Obteniendo empleado ID: {}", id);
        return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorId(id));
    }

    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoDTO> crear(@Valid @RequestBody EmpleadoRequestDTO dto) {
        log.info("Controller: Creando empleado");
        return new ResponseEntity<>(empleadoService.crearEmpleado(dto), HttpStatus.CREATED);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EmpleadoRequestDTO dto) {
        log.info("Controller: Actualizando empleado ID: {}", id);
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, dto));
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Controller: Eliminando empleado ID: {}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empleados/buscar")
    public ResponseEntity<List<EmpleadoDTO>> buscarPorSucursalYAnio(
            @RequestParam Integer sucursalId,
            @RequestParam Integer anio) {
        log.info("Controller: Buscando empleados por sucursal ID: {} y año de ingreso: {}", sucursalId, anio);
        List<EmpleadoDTO> empleados = empleadoService.buscarPorSucursalYAnio(sucursalId, anio);
        return ResponseEntity.ok(empleados);
    }
}