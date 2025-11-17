package org.sdia.stockservice.service;

import lombok.RequiredArgsConstructor;
import org.sdia.stockservice.dto.CreateStockDto;
import org.sdia.stockservice.dto.StockMarketDto;
import org.sdia.stockservice.entities.StockMarket;
import org.sdia.stockservice.repository.StockMarketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMarketServiceImpl implements StockMarketService {

    private final StockMarketRepository repository;

    @Override
    public StockMarketDto addStock(CreateStockDto dto) {
        StockMarket s = StockMarket.builder()
                .date(dto.getDate() != null ? dto.getDate() : LocalDateTime.now())
                .openValue(dto.getOpenValue())
                .closeValue(dto.getCloseValue())
                .volume(dto.getVolume())
                .companyId(dto.getCompanyId())
                .build();
        StockMarket saved = repository.save(s);

        // Mettre à jour le "prix actuel" de l'entreprise = closeValue de la dernière cotation
        updateCurrentPriceForCompany(saved.getCompanyId());

        return toDto(saved);
    }

    @Override
    public void deleteStock(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMarketDto> getAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StockMarketDto> getById(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    @Override
    public Double updateCurrentPriceForCompany(Long companyId) {
        List<StockMarket> list = repository.findByCompanyIdOrderByDateDesc(companyId);
        if (list.isEmpty()) return null;
        Double lastClose = list.get(0).getCloseValue();

        // Ici tu pourrais persister le "prix courant" dans une table Company ou cache.
        // Pour l'instant, on renvoie et log le prix courant.
        return lastClose;
    }

    @Override
    public List<StockMarketDto> getByCompanyId(Long companyId) {
        return repository.findByCompanyIdOrderByDateDesc(companyId).stream().map(this::toDto).collect(Collectors.toList());
    }

    private StockMarketDto toDto(StockMarket s) {
        return StockMarketDto.builder()
                .id(s.getId())
                .date(s.getDate())
                .openValue(s.getOpenValue())
                .closeValue(s.getCloseValue())
                .volume(s.getVolume())
                .companyId(s.getCompanyId())
                .build();
    }
}
