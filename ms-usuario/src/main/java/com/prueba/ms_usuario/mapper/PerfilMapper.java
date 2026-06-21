package com.prueba.ms_usuario.mapper;

import com.prueba.ms_usuario.dto.PerfilDTO;
import com.prueba.ms_usuario.dto.PerfilRequestDTO;
import com.prueba.ms_usuario.model.Perfil;
import com.prueba.ms_usuario.model.Usuario;
import org.springframework.stereotype.Component;

// Le dice a Spring que registre esta clase como un componente que se puede inyectar con @Autowired en otros lados
@Component
public class PerfilMapper {

    // Pasa la información de la entidad de la base de datos (Perfil) al molde que se le envía al cliente (PerfilDTO)
    public PerfilDTO toDTO(Perfil perfil) {
        if (perfil == null) return null;
        return new PerfilDTO(
                perfil.getId(),
                perfil.getTipoPerfil(),
                perfil.getDireccion(),
                perfil.getDescripcion(),
                perfil.isVerificado(),
                perfil.getFechaCreacion(),
                // Si el perfil tiene un usuario asociado, extrae su ID, si no, lo deja en null
                perfil.getUsuario() != null ? perfil.getUsuario().getId() : null
        );
    }

    // Toma los datos que envió el usuario (PerfilRequestDTO) y los convierte en un objeto Perfil listo para guardarse en la base de datos
    public Perfil toEntity(PerfilRequestDTO dto, Usuario usuario) {
        if (dto == null) return null;
        Perfil perfil = new Perfil();
        perfil.setTipoPerfil(dto.getTipoPerfil());
        perfil.setDireccion(dto.getDireccion());
        perfil.setDescripcion(dto.getDescripcion());
        perfil.setVerificado(dto.isVerificado());
        perfil.setFechaCreacion(dto.getFechaCreacion());
        // Vincula el usuario correspondiente con este perfil
        perfil.setUsuario(usuario);
        return perfil;
    }
}