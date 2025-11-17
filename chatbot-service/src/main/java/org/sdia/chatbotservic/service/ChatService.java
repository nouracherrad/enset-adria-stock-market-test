package org.sdia.chatbotservic.service;



import com.google.common.util.concurrent.Service;
import lombok.extern.slf4j.Slf4j;
import org.sdia.chatbotservic.models.ChatRequest;
import org.sdia.chatbotservic.models.ChatResponse;
import org.sdia.chatbotservic.models.Conversation;
import org.sdia.chatbotservic.models.StockDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ConversationRepository conversationRepository;
    private final LlmAgent llmAgent; // ✅ Utiliser votre agent LLM existant
    private final McpTools mcpTools; // ✅ Utiliser vos outils MCP existants

    private static final Pattern COMPANY_PATTERN =
            Pattern.compile("(AAPL|GOOGL|MSFT|TSLA|AMZN|META|NFLX)", Pattern.CASE_INSENSITIVE);

    @Transactional
    public ChatResponse processMessage(ChatRequest request) {
        try {
            // 1. Gérer la conversation
            Conversation conversation = manageConversation(request);

            // 2. Sauvegarder le message utilisateur
            saveUserMessage(conversation, request.getMessage());

            // 3. Analyser l'intention
            String intent = analyzeIntent(request.getMessage());

            // 4. Traiter selon l'intention
            ChatResponse response = processIntent(intent, request.getMessage(), request);

            // 5. Sauvegarder la réponse
            saveBotMessage(conversation, response.getResponse(), response.getAiModel());

            return response;

        } catch (Exception e) {
            log.error("Error processing chat message", e);
            return createErrorResponse();
        }
    }

    private Conversation manageConversation(ChatRequest request) {
        if (request.getTelegramChatId() != null) {
            return conversationRepository.findByTelegramChatId(request.getTelegramChatId())
                    .orElseGet(() -> createNewConversation(request));
        }
        return createNewConversation(request);
    }

    private Conversation createNewConversation(ChatRequest request) {
        Conversation conversation = new Conversation();
        conversation.setTelegramChatId(request.getTelegramChatId());
        conversation.setUserId(request.getUserId());
        conversation.setUsername(request.getUsername());
        return conversationRepository.save(conversation);
    }

    private void saveUserMessage(Conversation conversation, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setConversation(conversation);
        chatMessage.setMessageText(message);
        chatMessage.setIsBotMessage(false);
        // Sauvegarder via repository
    }

    private void saveBotMessage(Conversation conversation, String message, String aiModel) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setConversation(conversation);
        chatMessage.setMessageText(message);
        chatMessage.setIsBotMessage(true);
        chatMessage.setAiModelUsed(aiModel);
        // Sauvegarder via repository
    }

    private String analyzeIntent(String message) {
        String lowerMessage = message.toLowerCase();

        if (lowerMessage.contains("prix") || lowerMessage.contains("price")) {
            return "PRICE_QUERY";
        } else if (lowerMessage.contains("liste") || lowerMessage.contains("list")) {
            return "LIST_STOCKS";
        } else if (lowerMessage.contains("prediction") || lowerMessage.contains("prédiction")) {
            return "PREDICTION";
        } else if (lowerMessage.contains("aide") || lowerMessage.contains("help")) {
            return "HELP";
        } else {
            return "GENERAL_QUERY";
        }
    }

    private ChatResponse processIntent(String intent, String message, ChatRequest request) {
        switch (intent) {
            case "PRICE_QUERY":
                return handlePriceQuery(message);
            case "LIST_STOCKS":
                return handleListStocks();
            case "PREDICTION":
                return handlePrediction(message);
            case "HELP":
                return handleHelp();
            default:
                return handleGeneralQuery(message, request);
        }
    }

    private ChatResponse handlePriceQuery(String message) {
        // Utiliser MCP tools pour récupérer les données stocks
        var stockData = mcpTools.getStockPrice(extractCompanyId(message));

        ChatResponse response = new ChatResponse();
        response.setResponse(formatStockResponse(stockData));
        response.setAiModel("StockService");
        response.setTimestamp(LocalDateTime.now());
        response.setStockData(convertToStockDTO(stockData));

        return response;
    }

    private ChatResponse handleListStocks() {
        var stocks = mcpTools.getAllStocks();
        // Formater la réponse
        return createResponse("Liste des stocks...", "StockService");
    }

    private ChatResponse handlePrediction(String message) {
        // Utiliser l'agent LLM pour les prédictions
        var prediction = llmAgent.predictStock(extractCompanyId(message));
        return createResponse(prediction, "LLM-Prediction");
    }

    private ChatResponse handleHelp() {
        String helpText = """
            🤖 **Commandes disponibles:**
            - "Prix [entreprise]" : Obtenir le cours actuel
            - "Liste stocks" : Voir toutes les entreprises
            - "Prediction [entreprise]" : Prédiction de prix
            - "Aide" : Voir ce message
            """;
        return createResponse(helpText, "HelpSystem");
    }

    private ChatResponse handleGeneralQuery(String message, ChatRequest request) {
        // Utiliser l'agent LLM pour les questions générales
        var aiResponse = llmAgent.processQuery(message, request.getSessionId());
        return createResponse(aiResponse, "LLM-General");
    }



    private ChatResponse createErrorResponse() {
        return createResponse("❌ Erreur lors du traitement", "Error");
    }

    private String extractCompanyId(String message) {
        // Implémenter l'extraction du code entreprise
        var matcher = COMPANY_PATTERN.matcher(message.toUpperCase());
        return matcher.find() ? matcher.group(1) : "UNKNOWN";
    }

    private String formatStockResponse(Object stockData) {
        // Formater les données stock en texte lisible
        return "📊 Données stock formatées...";
    }

    private StockDTO convertToStockDTO(Object stockData) {
        // Convertir les données MCP en StockDTO
        return new StockDTO();
    }
}

