package com.prueba.ms_usuario.repository;

import com.prueba.ms_usuario.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Al heredar de JpaRepository, esta interfaz ya tiene listos los comandos para guardar, buscar, editar y borrar perfiles en la base de datos
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    // Query personalizada
    List<Perfil> findByTipoPerfilIgnoreCase(String tipoPerfil);
}