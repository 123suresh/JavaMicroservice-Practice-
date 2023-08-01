package com.microserviceprogramming.producetservice.service;

import com.microserviceprogramming.producetservice.dto.ProductRequest;
import com.microserviceprogramming.producetservice.dto.ProductResponse;
import com.microserviceprogramming.producetservice.model.Product;
import com.microserviceprogramming.producetservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    //now we are going to make use of constructor injection
    private final ProductRepository productRepository;

//    intead of generating this constructor we can add lombok annotation @RequireArgsConstructor
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    public void createProduct(ProductRequest productRequest){
        // now we have to map the product request to product model.
        //we will use product.builder method to build the product object.
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        //now object is created
        //now we have to save this object to database. for that we need to access product repository.
        productRepository.save(product);
        //we can also add some logs here by making use of @Slg4j lombok annotaion
        log.info("Product is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        //now we have to map this "Product" model to "ProductResponse" class for that we will use map.
        //we need to map each product to "ProductResponse" class
        //for this here we need to create object using a method.
        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
