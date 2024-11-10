package com.bly.fintech.handler;

import com.bly.fintech.controller.WebSocketController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebSocketController webSocketController;

    public WebSocketHandler(@Lazy WebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = session.getPrincipal().getName();
        sessions.put(userId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            JsonNode jsonPayload = objectMapper.readTree(message.getPayload());
            webSocketController.routeMessage(jsonPayload, session.getPrincipal().getName());
        } catch (JsonProcessingException e) {
            sendErrorMessage(session, "Invalid JSON format");
        } catch (IllegalArgumentException e) {
            sendErrorMessage(session, e.getMessage());
        }
    }

    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        try {
            String jsonError = objectMapper.writeValueAsString(Map.of(
                    "statusCode",400,
                    "type", "error",
                    "message", errorMessage
            ));
            session.sendMessage(new TextMessage(jsonError));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String sender, String message) {
        sessions.values().forEach(s -> {
            try {
                String jsonMessage = objectMapper.writeValueAsString(Map.of(
                        "statusCode",200,
                        "sender", sender,
                        "message", message,
                        "type", "broadcast"
                ));
                s.sendMessage(new TextMessage(jsonMessage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void sendToUser(String userId, String sender, String message) throws Exception {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            String jsonMessage = objectMapper.writeValueAsString(Map.of(
                    "statusCode",200,
                    "sender", sender,
                    "message", message,
                    "type", "private"
            ));
            session.sendMessage(new TextMessage(jsonMessage));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getPrincipal().getName());
    }
}
