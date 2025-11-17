package org.sdia.stockservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockMarketDto {
    private Long id;
    private LocalDateTime date;
    private Double openValue;
    private Double closeValue;
    private Long volume;
    private Long companyId;
}

