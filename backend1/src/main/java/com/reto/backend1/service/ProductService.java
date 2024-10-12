package com.reto.backend1.service;

import com.reto.backend1.model.Product;
import com.reto.backend1.repository.ProductRepository;

import org.springframework.kafka.core.KafkaTemplate;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductService {
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProductService(ProductRepository productRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Mono<Product> addProduct(Product product) {
        return productRepository.save(product)
                .doOnSuccess(savedProduct -> {
                    // Enviar mensaje a Kafka cuando se agrega un producto
                    kafkaTemplate.send("product-topic", "Producto agregado: " + savedProduct.getName());
                });
    }

    public Mono<Product> updateProduct(String id, Product product) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setQuantity(product.getQuantity());
                    return productRepository.save(existingProduct);
                })
                .doOnSuccess(updatedProduct -> {
                    // Enviar mensaje a Kafka cuando se actualiza un producto
                    kafkaTemplate.send("product-topic", "Producto actualizado: " + updatedProduct.getName());
                });
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
