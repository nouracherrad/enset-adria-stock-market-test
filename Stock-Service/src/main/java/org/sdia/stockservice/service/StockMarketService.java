package org.sdia.stockservice.service;


import org.sdia.stockservice.dto.CreateStockDto;
import org.sdia.stockservice.dto.StockMarketDto;

import java.util.List;
import java.util.Optional;

public interface StockMarketService {
    StockMarketDto addStock(CreateStockDto dto);
    void deleteStock(Long id);
    List<StockMarketDto> getAll();
    Optional<StockMarketDto> getById(Long id);
    Double updateCurrentPriceForCompany(Long companyId);
    List<StockMarketDto> getByCompanyId(Long companyId);
}

