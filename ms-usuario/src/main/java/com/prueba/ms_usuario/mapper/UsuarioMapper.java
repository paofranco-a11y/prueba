package com.prueba.ms_usuario.mapper;

import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.dto.UsuarioRequestDTO;
import com.prueba.ms_usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getEdad(),
                usuario.isActivo(),
                usuario.getFechaRegistro()
        );
    }

    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) return null;
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setEdad(dto.getEdad());
        usuario.setActivo(dto.isActivo());
        usuario.setFechaRegistro(dto.getFechaRegistro());
        return usuario;
    }
}