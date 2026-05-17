package com.prueba.ms_usuario.mapper;

import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.dto.PerfilRequestDTO;
import com.prueba.ms_usuario.model.Perfil;
import com.prueba.ms_usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {

    public PerfilDTO toDTO(Perfil perfil) {
        if (perfil == null) return null;
        return new PerfilDTO(
                perfil.getId(),
                perfil.getTipoPerfil(),
                perfil.getDireccion(),
                perfil.getDescripcion(),
                perfil.isVerificado(),
                perfil.getFechaCreacion(),
                perfil.getUsuario() != null ? perfil.getUsuario().getId() : null
        );
    }

    public Perfil toEntity(PerfilRequestDTO dto, Usuario usuario) {
        if (dto == null) return null;
        Perfil perfil = new Perfil();
        perfil.setTipoPerfil(dto.getTipoPerfil());
        perfil.setDireccion(dto.getDireccion());
        perfil.setDescripcion(dto.getDescripcion());
        perfil.setVerificado(dto.isVerificado());
        perfil.setFechaCreacion(dto.getFechaCreacion());
        perfil.setUsuario(usuario);
        return perfil;
    }
}