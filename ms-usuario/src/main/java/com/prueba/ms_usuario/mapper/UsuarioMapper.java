package com.prueba.ms_usuario.mapper;

import com.prueba.ms_usuario.dto.UsuarioDTO;
import com.prueba.ms_usuario.dto.UsuarioRequestDTO;
import com.prueba.ms_usuario.model.Usuario;
import org.springframework.stereotype.Component;

// Le dice a Spring que registre esta clase como un componente que se puede inyectar con @Autowired en otros lados
@Component
public class UsuarioMapper {

    // Convierte el objeto de la base de datos (Usuario) en el molde seguro para responderle al cliente (UsuarioDTO)
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

    // Toma los datos nuevos que llegaron en la petición (UsuarioRequestDTO) y los pasa a un objeto Usuario listo para la base de datos
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