package com.logistics.wmstms.tms;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ServicioSeguimientoEnvios {

    private final Map<String, SeguimientoEnvio> envios = Map.of(
        "ENV-1001", new SeguimientoEnvio("ENV-1001", "EN_TRANSITO", "Centro de distribucion Santa Fe"),
        "ENV-1002", new SeguimientoEnvio("ENV-1002", "ENTREGADO", "Rosario"),
        "ENV-1003", new SeguimientoEnvio("ENV-1003", "PENDIENTE_DESPACHO", "Deposito Parana")
    );

    public Optional<SeguimientoEnvio> buscarPorId(String idEnvio) {
        return Optional.ofNullable(envios.get(idEnvio.toUpperCase(Locale.ROOT)));
    }
}
