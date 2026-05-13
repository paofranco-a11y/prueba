package com.prueba.ms_usuario.mapper;

import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.dto.PerfilRequestDTO;
import com.prueba.ms_usuario.model.Perfil;
import com.prueba.ms_usuario.model.Usuario;

public class PerfilMapper {

    public static PerfilDTO toDTO(Perfil perfil) {
        return new PerfilDTO(
                perfil.getId(),
                perfil.getTipoPerfil(),
                perfil.getDireccion(),
                perfil.getDescripcion(),
                perfil.isVerificado(),
                perfil.getFechaCreacion(),
                perfil.getUsuario().getId()
        );
    }

    public static Perfil toEntity(PerfilRequestDTO dto, Usuario usuario) {
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