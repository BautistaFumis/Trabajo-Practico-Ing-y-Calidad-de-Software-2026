package com.logistics.wmstms.tms;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ControladorTransporte.class)
@Import(ServicioSeguimientoEnvios.class)
class ControladorTransporteTest {

    @Autowired
    private MockMvc clienteHttp;

    @Test
    void devuelveSeguimientoParaUnEnvioExistente() throws Exception {
        clienteHttp.perform(get("/api/tms/envios/ENV-1001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idEnvio").value("ENV-1001"))
            .andExpect(jsonPath("$.estado").value("EN_TRANSITO"))
            .andExpect(jsonPath("$.ubicacionActual").value("Centro de distribucion Santa Fe"));
    }

    @Test
    void devuelveSeguimientoParaEnvioPendienteDeDespacho() throws Exception {
        clienteHttp.perform(get("/api/tms/envios/ENV-1003"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idEnvio").value("ENV-1003"))
            .andExpect(jsonPath("$.estado").value("PENDIENTE_DESPACHO"))
            .andExpect(jsonPath("$.ubicacionActual").value("Deposito Parana"));
    }

    @Test
    void devuelveNoEncontradoParaUnEnvioDesconocido() throws Exception {
        clienteHttp.perform(get("/api/tms/envios/ENV-9999"))
            .andExpect(status().isNotFound());
    }
}
