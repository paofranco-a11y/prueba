package com.prueba.ms_usuario.runner;

import com.prueba.ms_usuario.model.Perfil;
import com.prueba.ms_usuario.model.Usuario;
import com.prueba.ms_usuario.repository.PerfilRepository;
import com.prueba.ms_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

// Hace que esta clase se ejecute sola al arrancar la aplicación
@Component
public class UsuarioRunner implements CommandLineRunner {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PerfilRepository perfilRepository;

    //Se activa solo para insertar los datos de prueba en la base de datos
    @Override
    public void run(String... args) throws Exception {



        if (!usuarioRepository.existsById(1)) {
            usuarioRepository.save(new Usuario(null, "Juan Perez", "juan@email.com", "912345678", 25, true, LocalDate.now(), null));
        }

        if (!usuarioRepository.existsById(2)) {
            usuarioRepository.save(new Usuario(null, "Maria Gonzalez", "maria@email.com", "987654321", 30, true, LocalDate.now(), null));
        }

        if (!usuarioRepository.existsById(3)) {
            usuarioRepository.save(new Usuario(null, "Carlos Lopez", "carlos@email.com", "956789012", 22, true, LocalDate.now(), null));
        }


        if (!perfilRepository.existsById(1)) {
            Usuario u1 = usuarioRepository.findById(1).orElse(null);
            perfilRepository.save(new Perfil(null, "Admin", "Calle 1, Santiago", "Perfil administrativo", false, LocalDate.now(), u1));
        }

        if (!perfilRepository.existsById(2)) {
            Usuario u2 = usuarioRepository.findById(2).orElse(null);
            perfilRepository.save(new Perfil(null, "Cliente", "Av. Principal 2, Valparaiso", "Perfil cliente regular", true, LocalDate.now(), u2));
        }

        if (!perfilRepository.existsById(3)) {
            Usuario u3 = usuarioRepository.findById(3).orElse(null);
            perfilRepository.save(new Perfil(null, "Vendedor", "Los Aromos 3, Concepcion", "Perfil vendedor activo", true, LocalDate.now(), u3));
        }

        // Muestra un mensaje en consola confirmando que los datos se cargaron
        System.out.println("Datos de Perfiles y Usuarios Cargados Correctamente");
    }
}