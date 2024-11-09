package com.bly.fintech.service;

import com.bly.fintech.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Message processMessage(Message message, SimpMessageHeaderAccessor headerAccessor) {
        // Broadcast to all connected users
        messagingTemplate.convertAndSend("/topic/public", message);
        return message;
    }

    public void sendPrivateMessage(Message message) {
        // Send to specific user by username
        messagingTemplate.convertAndSendToUser(message.getRecipientUsername(), "/queue/private", message);
    }
}
