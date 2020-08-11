package com.infosys.controller;

import com.infosys.exceptions.CustomerNotFoundException;
import com.infosys.service.AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/shelf")
public class ShelfController {

    @Autowired
    public AwsService awsService;

    @PostMapping("/put")
    public ResponseEntity putOnShelf(@RequestParam(value = "photo") MultipartFile photo,
                                     @RequestParam(value = "storeName") String storeName) {
        try {
            Optional<Long> entity = awsService.searchFace (storeName, photo);
        } catch(CustomerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getMessage(), e);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/take")
    public ResponseEntity takeFromShelf(@RequestBody MultipartFile photo, @RequestBody String storeName) {
        try {
            Optional<Long> entity = awsService.searchFace (storeName, photo);
        } catch(CustomerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getMessage(), e);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
