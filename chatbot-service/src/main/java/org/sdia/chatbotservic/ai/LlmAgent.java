package org.sdia.chatbotservic.ai;


import org.springframework.stereotype.Component;

@Component
public class LlmAgent {

    // Exemple simple d'interaction avec MCP / microservices
    public String generateResponse(String message) {
        // Ici tu peux appeler MCP tools pour récupérer données stock
        return "Réponse générée par l'agent AI pour: " + message;
    }
}

