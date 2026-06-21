package com.prueba.ms_usuario.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

// Define que esta clase es una entidad de base de datos mapeada a la tabla "perfil"
@Entity
@Table(name = "perfil") //Nombre de la tabla en la base de datos
@Data //crea automáticamente los métodos Getters y Setters para todas las variables
@NoArgsConstructor  //crea el constructor vacío
@AllArgsConstructor //crea un constructor con todos los parámetros
public class Perfil {

    // Identificador único autoincremental de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String tipoPerfil;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String descripcion;

    // Indica si el perfil pasó por el proceso de verificación (por defecto inicia en falso)
    @Column(nullable = false)
    private boolean verificado = false;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    // Relación de muchos perfiles a un solo usuario (Llave foránea usuario_id)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}