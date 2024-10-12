package com.reto.backend1.service;

import com.reto.backend1.model.Product;
import com.reto.backend1.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ProductService(ProductRepository productRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product)
                .doOnSuccess(savedProduct -> {
                    try {
                        // Convert the product to a JSON string
                        String productJson = objectMapper.writeValueAsString(savedProduct);
                        String message = "Creación de producto | " + productJson;
                        kafkaTemplate.send("product-topic", message);
                    } catch (Exception e) {
                        System.out.println("Error in saveProduct");
                        e.printStackTrace();
                    }
                });
    }

    public Mono<Product> updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    Product currentProduct = new Product();
                    currentProduct.setId(existingProduct.getId());
                    currentProduct.setName(existingProduct.getName());
                    currentProduct.setPrice(existingProduct.getPrice());
                    currentProduct.setQuantity(existingProduct.getQuantity());

                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setQuantity(product.getQuantity());

                    return productRepository.save(existingProduct)
                            .doOnSuccess(updatedProduct -> {
                                try {
                                    String currentProductJson = objectMapper.writeValueAsString(currentProduct);
                                    String updatedProductJson = objectMapper.writeValueAsString(updatedProduct);

                                    String message = "Actualización de producto (antes, después) | "
                                            + currentProductJson + " | " + updatedProductJson;

                                    kafkaTemplate.send("product-topic", message);
                                } catch (Exception e) {
                                    System.out.println("Error in updateProduct");
                                    e.printStackTrace();
                                }
                            });
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }
}
