package org.sdia.chatbotservic.models;



@Data
public class ChatRequest {
    private String message;
    private String telegramChatId;
    private Long userId;
    private String username;
    private String sessionId; // ✅ Ajouter
    private String language = "fr"; // ✅ Ajouter avec valeur par défaut
}
