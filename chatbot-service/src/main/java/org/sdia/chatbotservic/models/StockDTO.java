package org.sdia.chatbotservic.models;


import java.time.LocalDateTime;

public class StockDTO {
    private Long id;
    private LocalDateTime date;
    private double openValue;
    private double closeValue;
    private long volume;
    private Long companyId;
    private String companyName; // Ajouter
    private Double currentPrice; //  Ajouter
    private Double priceChange; // Ajouter
    private Double priceChangePercent;

    // Getters et Setters
}

