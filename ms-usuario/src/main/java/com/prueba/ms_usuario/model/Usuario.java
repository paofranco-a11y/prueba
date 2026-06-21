package com.prueba.ms_usuario.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

// Define que esta clase es una entidad de base de datos mapeada a la tabla "usuario"
@Entity
@Table(name = "usuario")
@Data //crea automáticamente los métodos Getters y Setters para todas las variables
@NoArgsConstructor  //crea el constructor vacío
@AllArgsConstructor //crea un constructor con todos los parámetros
public class Usuario {

    // Identificador único autoincremental de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    // Correo electrónico (campo obligatorio y único en el sistema)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Integer edad;

    // Estado del usuario en el sistema (activo/inactivo, por defecto verdadero)
    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    // Relación de un usuario a muchos perfiles. Si se borra el usuario, se borran sus perfiles en cascada.
    @OneToMany(mappedBy = "usuario",
            cascade = CascadeType.ALL)
    private List<Perfil> perfiles;
}