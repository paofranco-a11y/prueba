package com.prueba.ms_usuario.repository;

import com.prueba.ms_usuario.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    // Query personalizada
    List<Perfil> findByTipoPerfilIgnoreCase(String tipoPerfil);
}