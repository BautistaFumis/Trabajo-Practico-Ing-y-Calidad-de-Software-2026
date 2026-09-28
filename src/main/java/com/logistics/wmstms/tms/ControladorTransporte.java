package com.logistics.wmstms.tms;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tms")
public class ControladorTransporte {

    private final ServicioSeguimientoEnvios servicioSeguimientoEnvios;

    public ControladorTransporte(ServicioSeguimientoEnvios servicioSeguimientoEnvios) {
        this.servicioSeguimientoEnvios = servicioSeguimientoEnvios;
    }

    @GetMapping("/envios")
    public Map<String, Object> obtenerEstadoEnvios() {
        return Map.of(
            "modulo", "TMS - Sistema de Gestion de Transporte",
            "estado", "ACTIVO",
            "rutasActivas", 42,
            "entregasPendientes", 128
        );
    }

    @GetMapping("/envios/{idEnvio}")
    public ResponseEntity<SeguimientoEnvio> obtenerSeguimientoEnvio(@PathVariable String idEnvio) {
        return servicioSeguimientoEnvios.buscarPorId(idEnvio)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/envios/{idEnvio}/estado")
    public ResponseEntity<Map<String, String>> obtenerEstadoEnvio(@PathVariable String idEnvio) {
        return servicioSeguimientoEnvios.buscarPorId(idEnvio)
            .map(envio -> ResponseEntity.ok(Map.of(
                "idEnvio", envio.idEnvio(),
                "estado", envio.estado()
            )))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
