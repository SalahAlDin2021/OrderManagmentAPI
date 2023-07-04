/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/30/2023
 * Time: 7:57 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.controller;

import com.example.ordermanagementapi.dto.StockDTO;
import com.example.ordermanagementapi.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Stock")
@RestController
@RequestMapping("/api/v1/products")
public class StockController {

    @Autowired
    private StockService stockService;

    @Operation(summary = "Get all stocks of a specific product")
    @GetMapping("/{productId}/stocks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<StockDTO>> getAllStocksByProductId(@PathVariable Integer productId) {
        return ResponseEntity.ok(stockService.getAllStocksByProductId(productId));
    }

    @Operation(summary = "Get specific stock by stock ID of a specific product")
    @GetMapping("/{productId}/stocks/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StockDTO> getStockByProductIdAndStockId(@PathVariable Integer productId, @PathVariable Integer id) {
        return ResponseEntity.ok(stockService.getStockByProductIdAndStockId(productId, id));
    }

    @Operation(summary = "Create a new stock entry for a specific product")
    @PostMapping("/{productId}/stocks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StockDTO> createStockByProductId(@PathVariable Integer productId, @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(stockService.createStockByProductId(productId, stockDTO));
    }

    @Operation(summary = "Update stock for a specific product")
    @PutMapping("/{productId}/stocks/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StockDTO> updateStockByProductIdAndStockId(@PathVariable Integer productId, @PathVariable Integer id, @RequestBody StockDTO stockDTO) {
        return ResponseEntity.ok(stockService.updateStockByProductIdAndStockId(productId, id, stockDTO));
    }

    @Operation(summary = "Delete stock by stock ID of a specific product")
    @DeleteMapping("/{productId}/stocks/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteStockByProductIdAndStockId(@PathVariable Integer productId, @PathVariable Integer id) {
        stockService.deleteStockByProductIdAndStockId(productId, id);
        return ResponseEntity.ok().build();
    }
}
