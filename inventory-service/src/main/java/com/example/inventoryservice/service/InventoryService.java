package com.example.inventoryservice.service;

import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    //this annotation helps to automatically create and commit the transaction..
    public boolean isInStock(String skuCode){
        //here we donot have any method so we are makeing our own which is findBySkuCode().
        //so we should create this method inside repository
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
