package com.prueba.ms_usuario.service;

import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.dto.PerfilRequestDTO;
import com.prueba.ms_usuario.exception.ResourceNotFoundException;
import com.prueba.ms_usuario.mapper.PerfilMapper;
import com.prueba.ms_usuario.model.Perfil;
import com.prueba.ms_usuario.model.Usuario;
import com.prueba.ms_usuario.repository.PerfilRepository;
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
public class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilMapper perfilMapper;

    @InjectMocks
    private PerfilService perfilService;

    @Test
    void testListarPerfiles_Exitoso() {
        Perfil perfil = new Perfil();
        perfil.setId(1);

        when(perfilRepository.findAll()).thenReturn(Collections.singletonList(perfil));
        when(perfilMapper.toDTO(perfil)).thenReturn(new PerfilDTO());

        List<PerfilDTO> resultado = perfilService.listarPerfiles();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(perfilRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPerfilPorId_Exitoso() {
        Integer id = 1;
        Perfil perfil = new Perfil();
        perfil.setId(id);

        when(perfilRepository.findById(id)).thenReturn(Optional.of(perfil));
        when(perfilMapper.toDTO(perfil)).thenReturn(new PerfilDTO());

        PerfilDTO resultado = perfilService.obtenerPerfilPorId(id);

        assertNotNull(resultado);
        verify(perfilRepository, times(1)).findById(id);
    }

    @Test
    void testObtenerPerfilPorId_LanzaException_CuandoNoExiste() {
        Integer id = 99;
        when(perfilRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            perfilService.obtenerPerfilPorId(id);
        });
    }

    @Test
    void testCrearPerfil_Exitoso() {
        PerfilRequestDTO dto = new PerfilRequestDTO();
        dto.setUsuarioId(10);

        Usuario usuario = new Usuario();
        usuario.setId(10);

        Perfil perfil = new Perfil();
        perfil.setId(1);
        perfil.setUsuario(usuario);

        when(usuarioRepository.findById(10)).thenReturn(Optional.of(usuario));
        when(perfilMapper.toEntity(dto, usuario)).thenReturn(perfil);
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);
        when(perfilMapper.toDTO(perfil)).thenReturn(new PerfilDTO());

        PerfilDTO resultado = perfilService.crearPerfil(dto);

        assertNotNull(resultado);
        verify(perfilRepository, times(1)).save(any(Perfil.class));
    }

    @Test
    void testCrearPerfil_LanzaException_CuandoUsuarioNoExiste() {
        PerfilRequestDTO dto = new PerfilRequestDTO();
        dto.setUsuarioId(999);

        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            perfilService.crearPerfil(dto);
        });
        verify(perfilRepository, never()).save(any(Perfil.class));
    }

    @Test
    void testActualizarPerfil_Exitoso() {
        Integer id = 1;
        PerfilRequestDTO dto = new PerfilRequestDTO();
        dto.setUsuarioId(10);
        dto.setTipoPerfil("Premium");

        Perfil perfilExistente = new Perfil();
        perfilExistente.setId(id);

        Usuario usuario = new Usuario();
        usuario.setId(10);

        when(perfilRepository.findById(id)).thenReturn(Optional.of(perfilExistente));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(usuario));
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfilExistente);
        when(perfilMapper.toDTO(perfilExistente)).thenReturn(new PerfilDTO());

        PerfilDTO resultado = perfilService.actualizarPerfil(id, dto);

        assertNotNull(resultado);
        verify(perfilRepository, times(1)).save(perfilExistente);
    }

    @Test
    void testActualizarPerfil_LanzaException_CuandoPerfilNoExiste() {
        Integer id = 99;
        PerfilRequestDTO dto = new PerfilRequestDTO();

        when(perfilRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            perfilService.actualizarPerfil(id, dto);
        });
        verify(perfilRepository, never()).save(any(Perfil.class));
    }

    @Test
    void testActualizarPerfil_LanzaException_CuandoUsuarioNoExiste() {
        Integer id = 1;
        PerfilRequestDTO dto = new PerfilRequestDTO();
        dto.setUsuarioId(999);

        Perfil perfilExistente = new Perfil();
        perfilExistente.setId(id);

        when(perfilRepository.findById(id)).thenReturn(Optional.of(perfilExistente));
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            perfilService.actualizarPerfil(id, dto);
        });
        verify(perfilRepository, never()).save(any(Perfil.class));
    }

    @Test
    void testEliminarPerfil_Exitoso() {
        Integer id = 1;
        Perfil perfil = new Perfil();
        perfil.setId(id);

        when(perfilRepository.findById(id)).thenReturn(Optional.of(perfil));
        doNothing().when(perfilRepository).delete(perfil);

        assertDoesNotThrow(() -> {
            perfilService.eliminarPerfil(id);
        });

        verify(perfilRepository, times(1)).delete(perfil);
    }

    @Test
    void testEliminarPerfil_LanzaException_CuandoNoExiste() {
        Integer id = 99;
        when(perfilRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            perfilService.eliminarPerfil(id);
        });
        verify(perfilRepository, never()).delete(any(Perfil.class));
    }
}