package com.ecommerce.ms_envios.repository;

import com.ecommerce.ms_envios.model.Envio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Lee tu application.yml apuntando a prueba5_test
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EnvioRepositoryTest {

    @Autowired
    private EnvioRepository envioRepository;

    @Test
    void testGuardarYBuscarEnvio_Exitoso() {
        Envio envio = new Envio(null, 500, "Avenida Pruebas 123", 2500f, false, LocalDate.now());

        Envio guardado = envioRepository.save(envio);
        Optional<Envio> encontrado = envioRepository.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(500, encontrado.get().getPedidoId());
        assertEquals("Avenida Pruebas 123", encontrado.get().getDireccionDestino());
    }
}