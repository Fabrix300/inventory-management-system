package com.reto.backend2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public KafkaConsumerService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void consume(String message) {
        System.out.println("Mensaje recibido en backend 2: " + message);
        // Send notification through WebSocket
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
