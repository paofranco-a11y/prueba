package com.prueba.ms_productos;

import com.prueba.ms_productos.dto.CategoriaDTO;
import com.prueba.ms_productos.exception.ResourceNotFoundException;
import com.prueba.ms_productos.mapper.CategoriaMapper;
import com.prueba.ms_productos.model.Categoria;
import com.prueba.ms_productos.repository.CategoriaRepository;
import com.prueba.ms_productos.service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    // TEST 1: Listar todas las categorias
    @Test
    void deberiaRetornarListaDeCategorias() {
        Categoria categoria = new Categoria();
        categoria.setId(1);
        CategoriaDTO dto = new CategoriaDTO();

        when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        when(categoriaMapper.toDTO(categoria)).thenReturn(dto);

        List<CategoriaDTO> resultado = categoriaService.listarCategorias();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    // TEST 2: Obtener categoria por ID existente
    @Test
    void deberiaRetornarCategoriaPorId() {
        Categoria categoria = new Categoria();
        categoria.setId(1);
        CategoriaDTO dto = new CategoriaDTO();

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toDTO(categoria)).thenReturn(dto);

        CategoriaDTO resultado = categoriaService.obtenerCategoriaPorId(1);

        assertNotNull(resultado);
        verify(categoriaRepository, times(1)).findById(1);
    }

    // TEST 3: Obtener categoria por ID inexistente
    @Test
    void deberiaLanzarExcepcionCuandoCategoriaNoExiste() {
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            categoriaService.obtenerCategoriaPorId(99);
        });
    }

    // TEST 4: Eliminar categoria existente
    @Test
    void deberiaEliminarCategoriaExistente() {
        Categoria categoria = new Categoria();
        categoria.setId(1);

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).delete(categoria);

        categoriaService.eliminarCategoria(1);

        verify(categoriaRepository, times(1)).delete(categoria);
    }

    // TEST 5: Eliminar categoria inexistente lanza excepcion
    @Test
    void deberiaLanzarExcepcionAlEliminarCategoriaInexistente() {
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            categoriaService.eliminarCategoria(99);
        });
    }
}