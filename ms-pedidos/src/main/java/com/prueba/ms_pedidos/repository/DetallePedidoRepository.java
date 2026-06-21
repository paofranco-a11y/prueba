package com.prueba.ms_pedidos.repository;


import com.prueba.ms_pedidos.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Indica que esta interfaz maneja el acceso a datos para la tabla de detalles de pedido
@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
}

