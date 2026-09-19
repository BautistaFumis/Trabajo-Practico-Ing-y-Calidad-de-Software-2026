package com.logistics.wmstms.wms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/wms")
public class WarehouseController {

    @GetMapping("/stock")
    public Map<String, Object> getStockStatus() {
        return Map.of(
            "module", "WMS - Warehouse Management System",
            "status", "ACTIVE",
            "warehouseId", "WH-MAIN-01",
            "totalProducts", 1500
        );
    }
}
