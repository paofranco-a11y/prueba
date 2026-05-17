package com.prueba.ms_usuario.service;

import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.dto.UsuarioRequestDTO;
import com.prueba.ms_usuario.exception.ResourceNotFoundException;
import com.prueba.ms_usuario.mapper.UsuarioMapper;
import com.prueba.ms_usuario.model.Usuario;
import com.prueba.ms_usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    public List<UsuarioDTO> listarUsuarios() {
        log.info("Service: Listando todos los usuarios de la base de datos");
        try {
            return usuarioRepository.findAll().stream()
                    .map(usuarioMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Service: Error al listar usuarios: {}", e.getMessage());
            throw e;
        }
    }

    public UsuarioDTO obtenerUsuarioPorId(Integer id) {
        log.info("Service: Buscando usuario con ID: {}", id);
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            return usuarioMapper.toDTO(usuario);
        } catch (ResourceNotFoundException e) {
            log.error("Service: No se encontró el usuario con ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Service: Error al buscar usuario por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public UsuarioDTO crearUsuario(UsuarioRequestDTO dto) {
        log.info("Service: Intentando crear usuario con email: {}", dto.getEmail());
        try {
            Usuario usuario = usuarioMapper.toEntity(dto);
            Usuario guardado = usuarioRepository.save(usuario);
            log.info("Service: Usuario creado exitosamente con ID: {}", guardado.getId());
            return usuarioMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Service: Error al crear el usuario: {}", e.getMessage());
            throw e;
        }
    }

    public UsuarioDTO actualizarUsuario(Integer id, UsuarioRequestDTO dto) {
        log.info("Service: Intentando actualizar usuario ID: {}", id);
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

            usuario.setNombre(dto.getNombre());
            usuario.setEmail(dto.getEmail());
            usuario.setTelefono(dto.getTelefono());
            usuario.setEdad(dto.getEdad());
            usuario.setActivo(dto.isActivo());
            usuario.setFechaRegistro(dto.getFechaRegistro());

            Usuario actualizado = usuarioRepository.save(usuario);
            log.info("Service: Usuario ID: {} actualizado correctamente", id);
            return usuarioMapper.toDTO(actualizado);
        } catch (ResourceNotFoundException e) {
            log.error("Service: Error al actualizar, no existe ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Service: Error al actualizar usuario por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarUsuario(Integer id) {
        log.info("Service: Solicitud para eliminar usuario ID: {}", id);
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
            usuarioRepository.delete(usuario);
            log.info("Service: Usuario ID: {} eliminado", id);
        } catch (ResourceNotFoundException e) {
            log.error("Service: No se pudo eliminar, ID {} no encontrado", id);
            throw e;
        } catch (Exception e) {
            log.error("Service: Error al eliminar usuario por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public List<UsuarioDTO> buscarPorEmail(String email) {
        log.info("Service: Buscando usuarios activos por email: {}", email);
        try {
            return usuarioRepository.findByEmailAndActivo(email, true).stream()
                    .map(usuarioMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Service: Error al buscar usuarios por email {}: {}", email, e.getMessage());
            throw e;
        }
    }
}