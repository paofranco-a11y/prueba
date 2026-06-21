package com.prueba.ms_pedidos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// Define esta clase como una entidad de JPA para mapearla con la base de datos
@Entity
@Table(name = "detalles_pedidos")// Especifica el nombre de la tabla fisica en la base de datos MySQL
// Genera automaticamente los metodos getters, setters, toString y equals de Lombok
@Data
public class DetallePedido {

    // Define el atributo como la llave primaria de la tabla
    @Id
    // Configura que el id sea autoincrementable en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Columna para guardar el ID del producto comprado, no permite valores nulos
    @Column(name = "producto_id", nullable = false)
    private Integer productoId;

    @Column(nullable = false)
    private Integer cantidad;

    // Columna obligatoria para guardar el precio unitario del producto en ese momento
    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    // Columna para saber si tiene descuento, inicia por defecto en falso
    @Column(name = "descuento_aplicado")
    private Boolean descuentoAplicado = false;

    // Registra la fecha y hora de agregacion, toma el tiempo actual del sistema por defecto
    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado = LocalDateTime.now();

    // Define relacion de muchos a uno
    @ManyToOne(fetch = FetchType.LAZY)
    // Especifica el nombre de la columna que funcionara como llave foranea (FK)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}