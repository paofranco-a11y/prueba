package com.prueba.ms_productos.service;

import com.prueba.ms_productos.dto.CategoriaDTO;
import com.prueba.ms_productos.dto.CategoriaRequestDTO;
import com.prueba.ms_productos.exception.ResourceNotFoundException;
import com.prueba.ms_productos.mapper.CategoriaMapper;
import com.prueba.ms_productos.model.Categoria;
import com.prueba.ms_productos.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> listarCategorias() {
        log.info("Consultando todos los registros de categorias");
        try {
            return categoriaRepository.findAll().stream()
                    .map(categoriaMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al listar categorias: {}", e.getMessage());
            throw e;
        }
    }

    public CategoriaDTO obtenerCategoriaPorId(Integer id) {
        log.info("Buscando categoria con ID: {}", id);
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + id));
            return categoriaMapper.toDTO(categoria);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al buscar categoria por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public CategoriaDTO crearCategoria(CategoriaRequestDTO dto) {
        log.info("Creando una nueva categoria");
        try {
            Categoria categoria = categoriaMapper.toEntity(dto);
            return categoriaMapper.toDTO(categoriaRepository.save(categoria));
        } catch (Exception e) {
            log.error("Error al crear categoria: {}", e.getMessage());
            throw e;
        }
    }

    public CategoriaDTO actualizarCategoria(Integer id, CategoriaRequestDTO dto) {
        log.info("Actualizando categoria con ID: {}", id);
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + id));

            categoria.setNombre(dto.getNombre());
            categoria.setCodigo(dto.getCodigo());
            categoria.setDescripcion(dto.getDescripcion());
            categoria.setActivo(dto.isActivo());
            categoria.setFechaVigencia(dto.getFechaVigencia());

            return categoriaMapper.toDTO(categoriaRepository.save(categoria));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar categoria por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminarCategoria(Integer id) {
        log.info("Eliminando categoria con ID: {}", id);
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontro la categoria para eliminar con ID: " + id));
            categoriaRepository.delete(categoria);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar categoria por ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}