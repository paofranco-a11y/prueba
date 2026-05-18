package com.prueba.ms_productos.mapper;

import com.prueba.ms_productos.dto.CategoriaDTO;
import com.prueba.ms_productos.dto.CategoriaRequestDTO;
import com.prueba.ms_productos.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) return null;

        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setCodigo(categoria.getCodigo());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setActivo(categoria.isActivo());
        dto.setFechaVigencia(categoria.getFechaVigencia());

        return dto;
    }

    public Categoria toEntity(CategoriaRequestDTO dto) {
        if (dto == null) return null;

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setCodigo(dto.getCodigo());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setActivo(dto.isActivo());
        categoria.setFechaVigencia(dto.getFechaVigencia());

        return categoria;
    }
}