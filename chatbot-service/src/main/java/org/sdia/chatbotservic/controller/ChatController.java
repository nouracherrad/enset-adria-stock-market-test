package org.sdia.chatbotservic.controller;



import org.sdia.chatbotservic.models.ChatRequest;
import org.sdia.chatbotservic.models.ChatResponse;
import org.sdia.chatbotservic.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ChatResponse handleMessage(@RequestBody ChatRequest request) {
        return chatService.processMessage(request);
    }
}

