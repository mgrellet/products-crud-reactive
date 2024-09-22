package com.example.products.service;

import com.example.products.model.Product;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

  private static List<Product> products = new ArrayList<>(
      List.of(
          new Product(100, "Azucar", "Alimentación", 1.10, 20),
          new Product(101, "Leche", "Alimentación", 1.20, 15),
          new Product(102, "Jabón", "Limpieza", 0.89, 30),
          new Product(103, "Mesa", "Hogar", 125, 4),
          new Product(104, "Televisión", "Hogar", 650, 10),
          new Product(105, "Huevos", "Alimentación", 2.20, 30),
          new Product(106, "Fregona", "Limpieza", 3.40, 6),
          new Product(107, "Detergente", "Limpieza", 8.7, 12)));

  @Override
  public Flux<Product> getProducts() {
    return Flux.fromIterable(products).delayElements(Duration.ofSeconds(1));
    //return Flux.fromIterable(products);
  }

  @Override
  public Flux<Product> getProductsByCategory(String category) {
    return getProducts().filter(p -> p.getCategory().equals(category));
  }

  @Override
  public Mono<Product> getProductByCode(int code) {
    return getProducts().filter(p -> p.getCode() == code).next();
  }

  @Override
  public Mono<Void> save(Product product) {
    return getProductByCode(product.getCode())
        .switchIfEmpty(Mono.just(product).map(p ->{
          products.add(product);
          return p;
        })).then();

  }

  @Override
  public Mono<Product> delete(int code) {
    return getProductByCode(code)
        .map(product -> {
          products.removeIf(p -> p.getCode() == code);
          return product;
        });
  }

  @Override
  public Mono<Product> update(int code, double price) {
    return getProductByCode(code)
        .map(product -> {
          product.setPrice(price);
          return product;
        });
  }
}
