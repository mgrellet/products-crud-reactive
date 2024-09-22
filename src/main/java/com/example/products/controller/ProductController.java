package com.example.products.controller;

import com.example.products.model.Product;
import com.example.products.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductController {


  private final ProductService productService;

  ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/products")
  public ResponseEntity<Flux<Product>> getProducts() {
    return ResponseEntity.ok(productService.getProducts());
  }

  @GetMapping("/products/{category}")
  public ResponseEntity<Flux<Product>>
  getProductsByCategory(@PathVariable("category") String category) {
    return ResponseEntity.ok(productService.getProductsByCategory(category));
  }

  @GetMapping("/product")
  public ResponseEntity<Mono<Product>> getProductByCode(@RequestParam("code") int code) {
    return ResponseEntity.ok(productService.getProductByCode(code));
  }

  @PostMapping("/product")
  public ResponseEntity<Mono<Void>> save(@RequestBody Product product) {
    return ResponseEntity.ok(productService.save(product));
  }

  @PutMapping("/product")
  public Mono<ResponseEntity<Product>> update(@RequestParam("code") int code,
      @RequestParam("price") double price) {
    return productService.update(code, price)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @DeleteMapping("/product")
  public Mono<ResponseEntity<Product>> delete(@RequestParam("code") int code) {
    return productService.delete(code)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

}
