package com.example.products.service;

import com.example.products.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Flux<Product> getProducts();

  Flux<Product> getProductsByCategory(String category);

  Mono<Product> getProductByCode(int code);

  Mono<Void> save(Product product);

  Mono<Product> delete(int code);

  Mono<Product> update(int code, double price);

}
