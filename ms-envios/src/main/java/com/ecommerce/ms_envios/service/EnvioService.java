package com.ecommerce.ms_envios.service;

import com.ecommerce.ms_envios.client.PedidoClient;
import com.ecommerce.ms_envios.client.UsuarioClient;
import com.ecommerce.ms_envios.dto.EnvioRequestDTO;
import com.ecommerce.ms_envios.dto.EnvioResponseDTO;
import com.ecommerce.ms_envios.mapper.EnvioMapper;
import com.ecommerce.ms_envios.model.Envio;
import com.ecommerce.ms_envios.repository.EnvioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvioService {

    private static final Logger log = LoggerFactory.getLogger(EnvioService.class);

    private final EnvioRepository envioRepository;
    private final PedidoClient pedidoClient;
    private final UsuarioClient usuarioClient;

    public EnvioService(EnvioRepository envioRepository, PedidoClient pedidoClient, UsuarioClient usuarioClient) {
        this.envioRepository = envioRepository;
        this.pedidoClient = pedidoClient;
        this.usuarioClient = usuarioClient;
    }

    public List<EnvioResponseDTO> findAll() {
        log.info("Ejecutando método findAll para listar envíos");
        return envioRepository.findAll().stream()
                .map(EnvioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EnvioResponseDTO findById(Integer id) {
        log.info("Buscando envío por ID: {}", id);
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado con ID: " + id));
        return EnvioMapper.toDTO(envio);
    }

    public EnvioResponseDTO save(EnvioRequestDTO dto) {
        log.info("Intentando guardar un nuevo envío para el pedido ID: {}", dto.getPedidoId());
        try {
            // Validación cruzada vía FeignClients obligatorios de la rúbrica
            pedidoClient.obtenerPedidoPorId(dto.getPedidoId());

            Envio envio = EnvioMapper.toEntity(dto);
            Envio guardado = envioRepository.save(envio);
            return EnvioMapper.toDTO(guardado);
        } catch (Exception e) {
            log.error("Error crítico al procesar la creación del envío: {}", e.getMessage());
            throw new RuntimeException("Error en la comunicación entre microservicios o base de datos.");
        }
    }

    public EnvioResponseDTO update(Integer id, EnvioRequestDTO dto) {
        log.info("Actualizando envío ID: {}", id);
        Envio existente = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado para actualizar"));

        existente.setDireccionDestino(dto.getDireccionDestino());
        existente.setCostoEnvio(dto.getCostoEnvio());
        existente.setEsInternacional(dto.isEsInternacional());
        existente.setFechaDespacho(dto.getFechaDespacho());

        return EnvioMapper.toDTO(envioRepository.save(existente));
    }

    public void delete(Integer id) {
        log.info("Eliminando envío con ID: {}", id);
        Envio existente = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
        envioRepository.delete(existente);
    }
}
