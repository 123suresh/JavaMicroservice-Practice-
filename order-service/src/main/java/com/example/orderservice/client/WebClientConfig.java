package com.example.orderservice.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//we are going to maintain configuration so we will add configuration annotation
@Configuration
public class WebClientConfig {
    //inside this we are going to define a bean of type webclient
    //here we need make use of webclinet class but we don't find it here this is because this is our spring-mvc project
    //and that class is part of spring-web-flux so add to be able to use that add dependency of spring-web-flux
    //in pom.xml of order service.
    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
