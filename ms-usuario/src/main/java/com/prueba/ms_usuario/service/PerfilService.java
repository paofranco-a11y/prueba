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

// Activa los logs para poder mostrar mensajes informativos y de error
@Slf4j
// Le dice a Spring que esta clase maneja toda la lógica de negocio de los perfiles
@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilMapper perfilMapper;

    // Busca todos los perfiles de la base de datos y los convierte a DTO para devolverlos
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

    // Busca un perfil por su ID; si no lo encuentra, lanza un error personalizado
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

    // Valida que exista el usuario en la base de datos y le crea un nuevo perfil asignado
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

    // Busca el perfil y el usuario correspondientes y actualiza todos sus datos en la base de datos
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

    // Busca el perfil por su ID y, si existe, lo borra definitivamente de la base de datos
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