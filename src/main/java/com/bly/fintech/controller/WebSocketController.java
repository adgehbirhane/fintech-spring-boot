package com.bly.fintech.controller;

import com.bly.fintech.handler.WebSocketHandler;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final WebSocketHandler webSocketHandler;

    public WebSocketController(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void routeMessage(JsonNode jsonPayload, String sender) {
        String action = jsonPayload.get("action") != null ? jsonPayload.get("action").asText() : null;

        if (action == null || action.isEmpty()) {
            throw new IllegalArgumentException("Action field cannot be null or empty.");
        }

        try {
            if ("privateMessage".equals(action)) {
                String targetUser = jsonPayload.get("targetUser") != null ? jsonPayload.get("targetUser").asText() : null;
                String userMessage = jsonPayload.get("message") != null ? jsonPayload.get("message").asText() : null;

                if (targetUser == null || targetUser.isEmpty()) {
                    throw new IllegalArgumentException("Target user cannot be null or empty.");
                }

                if (userMessage == null || userMessage.isEmpty()) {
                    throw new IllegalArgumentException("Message field cannot be null or empty.");
                }

                webSocketHandler.sendToUser(targetUser, sender, userMessage);
            } else if ("broadcast".equals(action)) {
                String userMessage = jsonPayload.get("message") != null ? jsonPayload.get("message").asText() : null;

                if (userMessage == null || userMessage.isEmpty()) {
                    throw new IllegalArgumentException("Message field cannot be null or empty.");
                }

                webSocketHandler.broadcast(sender, userMessage);
            } else {
                throw new IllegalArgumentException("Invalid action type.");
            }
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
            throw e;
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
