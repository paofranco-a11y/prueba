package com.prueba.ms_inventario.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InventarioResponseDTO {
    private Integer id;
    private Integer productoId;
    private String ubicacionBodega;
    private Integer cantidadDisponible;
    private Integer stockMinimoAlerta;
    private Boolean activo;
    private LocalDate fechaUltimaRevision;
}
