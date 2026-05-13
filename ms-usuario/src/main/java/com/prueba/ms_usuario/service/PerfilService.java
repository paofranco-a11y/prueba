package com.prueba.ms_usuario.service;

import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.dto.PerfilRequestDTO;
import com.prueba.ms_usuario.exception.ResourceNotFoundException;
import com.prueba.ms_usuario.mapper.PerfilMapper;
import com.prueba.ms_usuario.model.Perfil;
import com.prueba.ms_usuario.model.Usuario;
import com.prueba.ms_usuario.repository.PerfilRepository;
import com.prueba.ms_usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    public List<PerfilDTO> listarPerfiles() {
        log.info("Service: Obteniendo listado de todos los perfiles");
        return perfilRepository.findAll()
                .stream()
                .map(PerfilMapper::toDTO)
                .toList();
    }

    public PerfilDTO obtenerPerfilPorId(Integer id) {
        log.info("Service: Buscando perfil ID: {}", id);
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Service: Perfil ID {} no encontrado", id);
                    return new ResourceNotFoundException("Perfil no encontrado");
                });
        return PerfilMapper.toDTO(perfil);
    }

    public PerfilDTO crearPerfil(PerfilRequestDTO dto) {
        try {
            log.info("Service: Creando perfil para usuario ID: {}", dto.getUsuarioId());
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

            Perfil perfil = PerfilMapper.toEntity(dto, usuario);
            Perfil guardado = perfilRepository.save(perfil);
            log.info("Service: Perfil ID {} creado y vinculado", guardado.getId());
            return PerfilMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Service: Error al crear perfil: {}", e.getMessage());
            throw e;
        }
    }

    public PerfilDTO actualizarPerfil(Integer id, PerfilRequestDTO dto) {
        log.info("Service: Actualizando datos del perfil ID: {}", id);
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        perfil.setTipoPerfil(dto.getTipoPerfil());
        perfil.setDireccion(dto.getDireccion());
        perfil.setDescripcion(dto.getDescripcion());
        perfil.setVerificado(dto.isVerificado());
        perfil.setFechaCreacion(dto.getFechaCreacion());
        perfil.setUsuario(usuario);

        Perfil actualizado = perfilRepository.save(perfil);
        log.info("Service: Perfil ID {} actualizado exitosamente", id);
        return PerfilMapper.toDTO(actualizado);
    }

    public void eliminarPerfil(Integer id) {
        log.info("Service: Eliminando perfil ID: {}", id);
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));

        perfilRepository.delete(perfil);
        log.info("Service: Perfil ID {} eliminado satisfactoriamente", id);
    }
}