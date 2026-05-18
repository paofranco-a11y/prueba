package com.prueba.ms_productos.mapper;

import com.prueba.ms_productos.dto.ProductoDTO;
import com.prueba.ms_productos.dto.ProductoRequestDTO;
import com.prueba.ms_productos.model.Categoria;
import com.prueba.ms_productos.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        if (producto == null) return null;
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCodigo(),
                producto.getPrecio(),
                producto.getStock(),
                producto.isActivo(),
                producto.getFechaRegistro(),
                producto.getCategoria() != null ? producto.getCategoria().getId() : null
        );
    }

    public Producto toEntity(ProductoRequestDTO dto, Categoria categoria) {
        if (dto == null) return null;

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCodigo(dto.getCodigo());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setActivo(dto.isActivo());
        producto.setFechaRegistro(dto.getFechaRegistro());
        producto.setCategoria(categoria);

        return producto;
    }
}