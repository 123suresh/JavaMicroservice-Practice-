package com.example.orderservice.service;

import com.example.orderservice.dto.InventoryResponse;
import com.example.orderservice.dto.OrderLineItemsDto;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineItems;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        //now we have to map the orderLineItems which is in from the order request
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        //now we will collect all the skucode from the order object
        List<String> skuCodes =order.getOrderLineItemsList().stream()
                .map(orderLineItem -> orderLineItem.getSkuCode())
                .toList();

        //now we should include this skucodes as request paramaters, because we have changed in inventory get api.
        //for that we will use builder in uri


        //call inventory service and place order if product is in stock
        //so for this we are going to use webclient, and for that we will create new package called config
        //and new class inside it called WebClientConfig.
        //basically we are making an synchronous communication bewtween two services that is between
        // order service and inventory service. synchromous means we will make a request and also depends on reponse.

        //we are exposing a get endpoint on inventory service and for that we need make a get call

        //in below code to retrieve data we retrieve,
        //by default webclient makes asyncronous request and we need to make synchronous request for that we
        //need to add .block()

        InventoryResponse[] inventoryArray = webClient.get()
                        .uri("http://localhost:8082/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                                .retrieve()
                //now we will make inventoryResponse as array
                                        .bodyToMono(InventoryResponse[].class)
                                                .block();
        boolean allProductsInStcock = Arrays.stream(inventoryArray)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if(allProductsInStcock){
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Product is not in stock please try again later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
