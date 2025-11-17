package org.sdia.stockservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_market")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // date/time de la cotation
    private LocalDateTime date;

    private Double openValue;
    private Double closeValue;
    private Long volume;

    // référence vers la company (id externe)
    private Long companyId;
}
