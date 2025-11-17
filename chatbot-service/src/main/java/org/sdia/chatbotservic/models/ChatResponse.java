package org.sdia.chatbotservic.models;


import java.time.LocalDateTime;
import java.util.Map;

public class ChatResponse {
    private String response;
    private String aiModel;
    private LocalDateTime timestamp;
    private StockDTO stockData; // Garder
    private String sentiment; //  Ajouter analyse de sentiment
    private Double confidence; //  Ajouter niveau de confiance
    private Map<String, Object> metadata;
}
