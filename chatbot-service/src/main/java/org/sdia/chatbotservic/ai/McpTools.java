package org.sdia.chatbotservic.ai;


import org.sdia.chatbotservic.models.StockDTO;
import org.springframework.stereotype.Component;

@Component
public class McpTools {

    public StockDTO getStockById(Long id) {
        return new StockDTO();
    }
}

