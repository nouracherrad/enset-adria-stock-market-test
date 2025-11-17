package org.sdia.chatbotservic.service;

import org.sdia.chatbotservic.models.ChatRequest;
import org.sdia.chatbotservic.models.ChatResponse;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBotService extends TelegramLongPollingBot {

    private String username;
    private String token;

    private final ChatService chatService;

    public TelegramBotService(ChatService chatService) {
        this.chatService = chatService; // injecté par Spring
    }

    // Méthode pour configurer username et token après création du bean
    public void configure(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            ChatRequest request = new ChatRequest();
            request.setUserId(chatId);
            request.setMessage(messageText);

            ChatResponse response = chatService.processMessage(request);

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(response.getResponse());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
