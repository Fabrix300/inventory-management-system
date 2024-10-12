package com.reto.backend2.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void consume(String message) {
        System.out.println("Mensaje recibido en backend 2: " + message);
        // TODO: What should I do now?
    }
}
