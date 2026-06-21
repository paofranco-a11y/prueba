package com.prueba.ms_pedidos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

// Indica a JPA que esta clase representa una tabla de la base de datos
@Entity
// Mapea la entidad con la tabla fisica llamada pedidos
@Table(name = "pedidos")
// Inyecta de forma automatica todos los metodos getters y setters con Lombok
@Data
public class Pedido {

    // Indica que este campo es el identificador unico o llave primaria
    @Id
    // Configura el ID para que se genere de manera autoincremental en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Almacena el ID del cliente dueño del pedido, campo obligatorio no nulo
    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    // Guarda el codigo de rastreo del envio, no nulo y con limite de 50 caracteres
    @Column(name = "codigo_seguimiento", nullable = false, length = 50)
    private String codigoSeguimiento;

    // Almacena el momento de creacion del pedido, se inicializa con la fecha actual
    @Column(name = "fecha_pedido")
    private LocalDateTime fechaPedido = LocalDateTime.now();

    // Registra el monto economico total acumulado del pedido, campo obligatorio
    @Column(nullable = false)
    private Double total;

    // Indica el estado del pago de la orden, por defecto inicia en falso
    @Column(nullable = false)
    private Boolean pagado = false;

    // Guarda la direccion fisica de entrega, obligatorio y limitado a 100 caracteres
    @Column(name = "direccion_envio", nullable = false, length = 100)
    private String direccionEnvio;

    // Define relacion de uno a muchos mapeada por el atributo pedido en la otra clase, con borrado en cascada y eliminacion de huerfanos activa
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;
}