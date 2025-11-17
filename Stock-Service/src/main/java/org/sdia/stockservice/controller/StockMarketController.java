package org.sdia.stockservice.controller;

import lombok.RequiredArgsConstructor;
import org.sdia.stockservice.dto.CreateStockDto;
import org.sdia.stockservice.dto.StockMarketDto;
import org.sdia.stockservice.service.StockMarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockMarketController {

    private final StockMarketService service;

    @PostMapping
    public ResponseEntity<StockMarketDto> add(@Validated @RequestBody CreateStockDto dto) {
        StockMarketDto created = service.addStock(dto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<StockMarketDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMarketDto> getOne(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<StockMarketDto>> getByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(service.getByCompanyId(companyId));
    }

    @PostMapping("/company/{companyId}/update-current-price")
    public ResponseEntity<Double> updateCurrentPrice(@PathVariable Long companyId) {
        Double price = service.updateCurrentPriceForCompany(companyId);
        if (price == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(price);
    }
}
