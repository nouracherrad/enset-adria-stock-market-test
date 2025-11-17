package org.sdia.chatbotservic.models;


import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @Column(name = "message_text", length = 4000)
    private String messageText;

    @Column(name = "is_bot_message")
    private Boolean isBotMessage;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "ai_model_used")
    private String aiModelUsed;

    @Column(name = "sentiment_score")
    private Double sentimentScore;

    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }
}
