///**
// * Author: Rifat Shariar Sakil
// * Time: 4:16 PM
// * Date: 2/9/25
// * Project Name: ekhonni-v2
// */
//
//package com.ekhonni.backend.config;
//
//
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
//    }
//}
//
