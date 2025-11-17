package org.sdia.stockservice.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateStockDto {
    private LocalDateTime date;
    @NotNull
    private Double openValue;
    @NotNull
    private Double closeValue;
    @NotNull
    private Long volume;
    @NotNull
    private Long companyId;
}
