package com.prueba.ms_usuario.service;

import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.dto.UsuarioRequestDTO;
import com.prueba.ms_usuario.exception.ResourceNotFoundException;
import com.prueba.ms_usuario.mapper.UsuarioMapper;
import com.prueba.ms_usuario.model.Usuario;
import com.prueba.ms_usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testListarUsuarios_Exitoso() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(new UsuarioDTO());

        List<UsuarioDTO> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testObtenerUsuarioPorId_Exitoso() {
        Integer id = 1;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(new UsuarioDTO());

        UsuarioDTO resultado = usuarioService.obtenerUsuarioPorId(id);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void testObtenerUsuarioPorId_LanzaException_CuandoNoExiste() {
        Integer id = 99;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.obtenerUsuarioPorId(id);
        });
    }

    @Test
    void testCrearUsuario_Exitoso() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setEmail("test@prueba.com");

        Usuario usuario = new Usuario();
        usuario.setId(1);

        when(usuarioMapper.toEntity(dto)).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(new UsuarioDTO());

        UsuarioDTO resultado = usuarioService.crearUsuario(dto);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testActualizarUsuario_Exitoso() {
        Integer id = 1;
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNombre("Nuevo Nombre");
        dto.setEmail("nuevo@prueba.com");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);
        when(usuarioMapper.toDTO(usuarioExistente)).thenReturn(new UsuarioDTO());

        UsuarioDTO resultado = usuarioService.actualizarUsuario(id, dto);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(usuarioExistente);
    }

    @Test
    void testActualizarUsuario_LanzaException_CuandoNoExiste() {
        Integer id = 99;
        UsuarioRequestDTO dto = new UsuarioRequestDTO();

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.actualizarUsuario(id, dto);
        });
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario_Exitoso() {
        Integer id = 1;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        assertDoesNotThrow(() -> {
            usuarioService.eliminarUsuario(id);
        });

        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void testEliminarUsuario_LanzaException_CuandoNoExiste() {
        Integer id = 99;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.eliminarUsuario(id);
        });
        verify(usuarioRepository, never()).delete(any(Usuario.class));
    }

    @Test
    void testBuscarPorEmail_Exitoso() {
        String email = "buscar@prueba.com";
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setEmail(email);
        usuario.setActivo(true);

        when(usuarioRepository.findByEmailAndActivo(email, true)).thenReturn(Collections.singletonList(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(new UsuarioDTO());

        List<UsuarioDTO> resultado = usuarioService.buscarPorEmail(email);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(usuarioRepository, times(1)).findByEmailAndActivo(email, true);
    }
}