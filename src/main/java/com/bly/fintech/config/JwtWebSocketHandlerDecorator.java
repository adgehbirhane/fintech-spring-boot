//package com.bly.fintech.config;
//
//import com.bly.fintech.util.JwtUtils;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
//import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
//import org.springframework.http.server.ServerHttpRequest;
//
//public class JwtWebSocketHandlerDecorator extends WebSocketHandlerDecorator {
//
//    private final JwtUtils jwtUtil;
//
//    public JwtWebSocketHandlerDecorator(WebSocketHandler handler, JwtUtils jwtUtil) {
//        super(handler);
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // Extract JWT token from the handshake request
//        ServerHttpRequest request = (ServerHttpRequest) session.getAttributes().get("HTTP_REQUEST");
//        String token = request.getHeaders().getFirst("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // Extract token part after "Bearer "
//
//            if (jwtUtil.validateJwtToken(token)) {
//                String username = jwtUtil.getUsernameFromJwtToken(token);
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        username, null, java.util.Collections.emptyList());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//        super.afterConnectionEstablished(session);
//    }
//}
