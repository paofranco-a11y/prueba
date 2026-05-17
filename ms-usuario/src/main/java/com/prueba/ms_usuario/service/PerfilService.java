package com.prueba.ms_usuario.service;

import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.dto.PerfilRequestDTO;
import com.prueba.ms_usuario.exception.ResourceNotFoundException;
import com.prueba.ms_usuario.mapper.PerfilMapper;
import com.prueba.ms_usuario.model.Perfil;
import com.prueba.ms_usuario.model.Usuario;
import com.prueba.ms_usuario.repository.PerfilRepository;
import com.prueba.ms_usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilMapper perfilMapper;

    public List<PerfilDTO> listarPerfiles() {
        log.info("Service: Obteniendo listado de todos los perfiles");
        try {
            return perfilRepository.findAll().stream()
                    .map(perfilMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Service: Error al listar perfiles: {}", e.getMessage());
            throw e;
        }
    }

    public PerfilDTO obtenerPerfilPorId(Integer id) {
        log.info("Service: Buscando perfil ID: {}", id);
        try {
            Perfil perfil = perfilRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));
            return perfilMapper.toDTO(perfil);
        } catch (ResourceNotFoundException e) {
            log.error("Service: Perfil ID {} no encontrado", id);
            throw e;
        } catch (Exception e) {
            log.error("Service: Error al obtener perfil por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public PerfilDTO crearPerfil(PerfilRequestDTO dto) {
        log.info("Service: Creando perfil para usuario ID: {}", dto.getUsuarioId());
        try {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

            Perfil perfil = perfilMapper.toEntity(dto, usuario);
            Perfil guardado = perfilRepository.save(perfil);
            log.info("Service: Perfil ID {} creado y vinculado", guardado.getId());
            return perfilMapper.toDTO(guardado);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Service: Error al crear perfil: {}", e.getMessage());
            throw e;
        }
    }

    public PerfilDTO actualizarPerfil(Integer id, PerfilRequestDTO dto) {
        log.info("Service: Actualizando datos del perfil ID: {}", id);
        try {
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
            return perfilMapper.toDTO(actualizado);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Service: Error al actualizar perfil por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarPerfil(Integer id) {
        log.info("Service: Eliminando perfil ID: {}", id);
        try {
            Perfil perfil = perfilRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));

            perfilRepository.delete(perfil);
            log.info("Service: Perfil ID {} eliminado satisfactoriamente", id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Service: Error al eliminar perfil por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}