//package com.bly.fintech.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
//
//@Configuration
//@EnableWebSocketSecurity
//public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//
//    @Override
//    protected boolean sameOriginDisabled() {
//        return true;  // Allow WebSocket connections from any origin
//    }
//
//    @Override
//    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
//        messages
//                .simpDestMatchers("/app/**").authenticated()  // Secure /app/** endpoints
//                .anyMessage().authenticated();  // Any other messages require authentication
//    }
//}
