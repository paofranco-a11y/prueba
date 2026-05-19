package com.prueba.ms_pedidos.repository;


import com.prueba.ms_pedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
}

