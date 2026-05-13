package com.prueba.ms_usuario.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "usuario",
            cascade = CascadeType.ALL)
            private List<Perfil> perfiles;
}