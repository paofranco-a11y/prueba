package com.ecommerce.ms_envios.repository;


import com.ecommerce.ms_envios.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Integer> {

    @Query("SELECT e FROM Envio e WHERE e.fechaDespacho BETWEEN :inicio AND :fin " +
            "AND NOT EXISTS (SELECT s FROM Seguimiento s WHERE s.envio = e AND s.estadoActual = 'ENTREGADO')")
    List<Envio> buscarEnviosNoEntregadosEnRango(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}