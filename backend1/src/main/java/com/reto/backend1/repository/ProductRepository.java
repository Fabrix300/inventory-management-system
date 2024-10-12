package com.reto.backend1.repository;

import com.reto.backend1.model.Product;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ProductRepository extends R2dbcRepository<Product, Long> {
}
