package com.prueba.ms_proveedores.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "proveedor")
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String rut;

    @Column(nullable = false)
    private Integer calificacion;

    private boolean activo = true;

    private String contactoEmail;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<Contrato> contratos;
}