package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    //this annotation helps to automatically create and commit the transaction..
    public List<InventoryResponse> isInStock(List<String> skuCode){
        //here we donot have any method so we are makeing our own which is findBySkuCode().
        //so we should create this method inside repository
        //below line we are querying the repo to find out all the  inventory objects for this given skucode.
        //Then mapping the inventory object to the inventoryResponse object and finally we are returning list of
        //inventoryResponse object as a response
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
