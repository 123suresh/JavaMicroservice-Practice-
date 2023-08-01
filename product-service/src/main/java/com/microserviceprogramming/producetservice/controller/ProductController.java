package com.microserviceprogramming.producetservice.controller;

import com.microserviceprogramming.producetservice.dto.ProductRequest;
import com.microserviceprogramming.producetservice.dto.ProductResponse;
import com.microserviceprogramming.producetservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    // you can add below constructor or just use @RequiredArgsConstructor lombok annotaion
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //here we are going to receive product information for that we are going to create new class
    // called ProductRequest that is going to act as dto.
    //we are receiving ProductRequest as request body.
    //rest proessing we will do in the service layer.
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    //now we are going to create another endpoint to retrieve all the product
    //for that we are going to create another response called ProductResponse


    //now for getting product we will create a ProductResponse in dto instead of using existing product model.
    //because it is not good to expose model in outer world. there may be some data which is not good
    //to show in outer world. dto stands for data transfer object.
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
