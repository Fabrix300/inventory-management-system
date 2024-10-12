package com.reto.backend1.controller;

import com.reto.backend1.service.ProductService;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
}
