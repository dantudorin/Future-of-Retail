package com.infosys.service;

import com.infosys.exceptions.CustomerNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class ShelfService {
    public AwsService awsService = new AwsService();

    public Long putOnShelf(MultipartFile photo, String storeName) throws CustomerNotFoundException {
        Optional<Long> entity = awsService.searchFace (storeName, photo);
        Long customerId = entity.get();
        return customerId;
    }

    public Long takeFromShelf(MultipartFile photo, String storeName) throws CustomerNotFoundException {
        Optional<Long> entity = awsService.searchFace (storeName, photo);
        Long customerId = entity.get();
        return customerId;
    }
}
