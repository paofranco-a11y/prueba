package com.prueba.ms_usuario.service;

import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.dto.UsuarioRequestDTO;
import com.prueba.ms_usuario.exception.ResourceNotFoundException;
import com.prueba.ms_usuario.mapper.UsuarioMapper;
import com.prueba.ms_usuario.model.Usuario;
import com.prueba.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listarUsuarios() {
        log.info("Service: Listando todos los usuarios de la base de datos");
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    public UsuarioDTO obtenerUsuarioPorId(Integer id) {
        log.info("Service: Buscando usuario con ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service: No se encontró el usuario con ID: {}", id);
                    return new ResourceNotFoundException("Usuario no encontrado");
                });
        return UsuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO crearUsuario(UsuarioRequestDTO dto) {
        try {
            log.info("Service: Intentando crear usuario con email: {}", dto.getEmail());
            Usuario usuario = UsuarioMapper.toEntity(dto);
            Usuario guardado = usuarioRepository.save(usuario);
            log.info("Service: Usuario creado exitosamente con ID: {}", guardado.getId());
            return UsuarioMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Service: Error al crear el usuario: {}", e.getMessage());
            throw e;
        }
    }

    public UsuarioDTO actualizarUsuario(Integer id, UsuarioRequestDTO dto) {
        log.info("Service: Intentando actualizar usuario ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service: Error al actualizar, no existe ID: {}", id);
                    return new ResourceNotFoundException("Usuario no encontrado");
                });

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setEdad(dto.getEdad());
        usuario.setActivo(dto.isActivo());
        usuario.setFechaRegistro(dto.getFechaRegistro());

        Usuario actualizado = usuarioRepository.save(usuario);
        log.info("Service: Usuario ID: {} actualizado correctamente", id);
        return UsuarioMapper.toDTO(actualizado);
    }

    public void eliminarUsuario(Integer id) {
        log.info("Service: Solicitud para eliminar usuario ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service: No se pudo eliminar, ID {} no encontrado", id);
                    return new ResourceNotFoundException("Usuario no encontrado");
                });
        usuarioRepository.delete(usuario);
        log.info("Service: Usuario ID: {} eliminado", id);
    }

    public List<UsuarioDTO> buscarPorEmail(String email) {
        log.info("Service: Buscando usuarios activos por email: {}", email);
        return usuarioRepository
                .findByEmailAndActivo(email, true)
                .stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }
}