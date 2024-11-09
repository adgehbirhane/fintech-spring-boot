package com.bly.fintech.controller;

import com.bly.fintech.model.Message;
import com.bly.fintech.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        return messageService.processMessage(message, headerAccessor);
    }

    @MessageMapping("/chat.private")
    public void sendPrivateMessage(@Payload Message message) {
        messageService.sendPrivateMessage(message);
    }
}
