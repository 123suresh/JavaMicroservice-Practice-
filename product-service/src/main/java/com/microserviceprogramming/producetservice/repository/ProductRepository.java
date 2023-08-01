package com.microserviceprogramming.producetservice.repository;

import com.microserviceprogramming.producetservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
