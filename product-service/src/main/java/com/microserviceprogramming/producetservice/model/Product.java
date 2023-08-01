package com.microserviceprogramming.producetservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

//to define this product as mongodb document add below annotation
@Document(value = "product")
//add the lomberg annotation to generate getter and setter methods and constructors.
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    //to specify as unique indentifier  for our product we use @Id annoatation.
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
