package com.prueba.ms_usuario.repository;

import com.prueba.ms_usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByEmailAndActivo(String email, boolean activo);

}