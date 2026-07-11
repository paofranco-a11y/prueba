package com.ecommerce.ms_empleados.service;

import com.ecommerce.ms_empleados.client.SucursalClient;
import com.ecommerce.ms_empleados.dto.EmpleadoRequestDTO;
import com.ecommerce.ms_empleados.model.Empleado;
import com.ecommerce.ms_empleados.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private SucursalClient sucursalClient;

    @InjectMocks
    private EmpleadoService empleadoService;

    private Empleado empleado;
    private EmpleadoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        empleado = new Empleado(
                1,
                "Juan Pérez",
                "juan.perez@ecommerce.com",
                850000f,
                3,
                true,
                LocalDate.of(2024, 3, 15)
        );

        requestDTO = new EmpleadoRequestDTO(
                "Juan Pérez",
                "juan.perez@ecommerce.com",
                850000f,
                3,
                true,
                LocalDate.of(2024, 3, 15)
        );
    }

    // ============ findAll (V1) ============

    @Test
    void findAll_deberiaRetornarListaDeEmpleados() {
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));

        var resultado = empleadoService.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreCompleto()).isEqualTo("Juan Pérez");
        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    void findAll_deberiaRetornarListaVacia_cuandoNoHayEmpleados() {
        when(empleadoRepository.findAll()).thenReturn(Collections.emptyList());

        var resultado = empleadoService.findAll();

        assertThat(resultado).isEmpty();
    }

    // ============ findById (V1) ============

    @Test
    void findById_deberiaRetornarEmpleado_cuandoExiste() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        var resultado = empleadoService.findById(1);

        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getCorreoElectronico()).isEqualTo("juan.perez@ecommerce.com");
    }

    @Test
    void findById_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> empleadoService.findById(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ResourceNotFound");
    }

    // ============ save (V1) ============

    @Test
    void save_deberiaGuardarEmpleado_cuandoSucursalEsValida() {
        when(sucursalClient.obtenerSucursalPorId(3)).thenReturn(null); // no nos interesa el body, solo que no lance excepción
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);

        var resultado = empleadoService.save(requestDTO);

        assertThat(resultado.getNombreCompleto()).isEqualTo("Juan Pérez");
        verify(sucursalClient, times(1)).obtenerSucursalPorId(3);
        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    void save_deberiaLanzarExcepcion_cuandoSucursalNoExiste() {
        when(sucursalClient.obtenerSucursalPorId(anyInt()))
                .thenThrow(new RuntimeException("Sucursal no encontrada"));

        assertThatThrownBy(() -> empleadoService.save(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se pudo guardar el empleado");

        verify(empleadoRepository, never()).save(any());
    }

    // ============ update (V1) ============

    @Test
    void update_deberiaActualizarCamposCorrectamente() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(inv -> inv.getArgument(0));

        requestDTO.setNombreCompleto("Juan Pérez Actualizado");
        var resultado = empleadoService.update(1, requestDTO);

        assertThat(resultado.getNombreCompleto()).isEqualTo("Juan Pérez Actualizado");
        verify(empleadoRepository).save(empleado);
    }

    @Test
    void update_deberiaLanzarExcepcion_cuandoEmpleadoNoExiste() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> empleadoService.update(1, requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existe");
    }

    // ============ delete ============

    @Test
    void delete_deberiaEliminarEmpleado_cuandoExiste() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        empleadoService.delete(1);

        verify(empleadoRepository, times(1)).delete(empleado);
    }

    @Test
    void delete_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> empleadoService.delete(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existe");

        verify(empleadoRepository, never()).delete(any());
    }

    // ============ V2 - findAllEntities / findEntityById ============

    @Test
    void findAllEntities_deberiaRetornarEntidadesCrudas() {
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado));

        var resultado = empleadoService.findAllEntities();

        assertThat(resultado).containsExactly(empleado);
    }

    @Test
    void findEntityById_deberiaRetornarEntidad_cuandoExiste() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));

        var resultado = empleadoService.findEntityById(1);

        assertThat(resultado).isEqualTo(empleado);
    }

    @Test
    void findEntityById_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(empleadoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> empleadoService.findEntityById(99))
                .isInstanceOf(RuntimeException.class);
    }

    // ============ V2 - saveEntity ============

    @Test
    void saveEntity_deberiaGuardarEntidad_cuandoSucursalEsValida() {
        when(sucursalClient.obtenerSucursalPorId(3)).thenReturn(null);
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);

        var resultado = empleadoService.saveEntity(requestDTO);

        assertThat(resultado).isEqualTo(empleado);
        verify(empleadoRepository).save(any(Empleado.class));
    }

    @Test
    void saveEntity_deberiaLanzarExcepcion_cuandoSucursalFalla() {
        when(sucursalClient.obtenerSucursalPorId(anyInt()))
                .thenThrow(new RuntimeException("Sucursal no responde"));

        assertThatThrownBy(() -> empleadoService.saveEntity(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se pudo guardar la entidad empleado");
    }

    // ============ V2 - updateEntity ============

    @Test
    void updateEntity_deberiaActualizarYRetornarEntidad() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(inv -> inv.getArgument(0));

        requestDTO.setSueldoBase(1000000f);
        var resultado = empleadoService.updateEntity(1, requestDTO);

        assertThat(resultado.getSueldoBase()).isEqualTo(1000000f);
    }

    @Test
    void updateEntity_deberiaLanzarExcepcion_cuandoNoExiste() {
        when(empleadoRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> empleadoService.updateEntity(1, requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no existe para modificar");
    }
}