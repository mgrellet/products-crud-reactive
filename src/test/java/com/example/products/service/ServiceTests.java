package com.example.products.service;

import com.example.products.model.Product;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ServiceTests {

  @Autowired
  ProductService productService;

  @Test
  @Order(1)
  void testGetProductsByCategory() {
    StepVerifier.create(productService.getProductsByCategory("Hogar"))
        .expectNextMatches(p -> "Mesa".equals(p.getName()))
        .expectNextMatches(p -> "Televisión".equals(p.getName()))
        .verifyComplete();
  }

  @Test
  @Order(2)
  void testGetProducts() {
    StepVerifier.create(productService.getProducts())
        .expectNextCount(8)
        .verifyComplete();
  }

  @Test
  @Order(3)
  void testSaveProducts() {
    Product product = Product
        .builder().code(108).name("Prod").category("Cat").price(1).stock(1).build();

    StepVerifier.create(productService.save(product))
        .expectComplete()
        .verify();

  }

  @Test
  @Order(4)
  void testDeleteProducts() {
    StepVerifier.create(productService.delete(103))
        .expectNextMatches(p -> "Mesa".equals(p.getName()))
        .verifyComplete();
  }
}
