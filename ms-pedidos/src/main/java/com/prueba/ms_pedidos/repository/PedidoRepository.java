package com.prueba.ms_pedidos.repository;

import com.prueba.ms_pedidos.model.DetallePedido;
import com.prueba.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {


    @Query("SELECT p FROM Pedido p WHERE p.pagado = true ORDER BY p.fechaPedido DESC")
    List<Pedido> findPedidosPagados();
}