package com.microserviceprogramming.producetservice;

import com.microserviceprogramming.producetservice.dto.ProductRequest;
import com.microserviceprogramming.producetservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.http.ResponseEntity.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProducetServiceApplicationTests {

	//now here we are going to write integration test
	//here we will test whether the post and get apis are working correctly or not.
	//first we will install a "TestContainers" library.
	//this library provides some access to some external software we need like DB, web browser,  rabbitmq
	//we can run this external software as docker containers and we can run our integration test

	//now  add multiple Testcontainers dependencies of Maven in pom.xml the code is
//	<dependencyManagement>
//    <dependencies>
//        <dependency>
//            <groupId>org.testcontainers</groupId>
//            <artifactId>testcontainers-bom</artifactId>
//            <version>1.18.3</version>
//            <type>pom</type>
//            <scope>import</scope>
//        </dependency>
//    </dependencies>
//</dependencyManagement>

	// now here we are using mongoDB so add mmaven module of mongoDB.
//<dependency>
//    <groupId>org.testcontainers</groupId>
//    <artifactId>mongodb</artifactId>
//    <version>1.18.3</version>
//    <scope>test</scope>
//</dependency>

	//we also need to add junit module.

	//first we need to add the test container annoation.
	//now we will define mongoDB container inside our test.

	// we are adding container anntation here so that junit file understand that this is mongoDB container
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	//if you see on application.properties we have (spring.data.mongodb.uri) and we need to provide here
	//because we are doing integration test for that we need @DynamicPropertySource annotation

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	//now lets write test for create product


//	@Test
//	void contextLoads() {
//	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString));
//				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private ProductRequest getProductRequest(){
		return ProductRequest.builder()
				.name("iphone 13")
				.description("iphone 13")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

}
