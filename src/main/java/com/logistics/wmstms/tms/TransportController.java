package com.logistics.wmstms.tms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tms")
public class TransportController {

    @GetMapping("/shipments")
    public Map<String, Object> getShipmentsStatus() {
        return Map.of(
            "module", "TMS - Transportation Management System",
            "status", "ACTIVE",
            "activeRoutes", 42,
            "pendingDeliveries", 128
        );
    }
}
