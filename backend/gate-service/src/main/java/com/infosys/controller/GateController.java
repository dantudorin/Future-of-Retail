package com.infosys.controller;

import java.util.List;
import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import com.infosys.model.Customer;
import com.infosys.service.GateService;
import org.springframework.http.HttpStatus;
import com.infosys.requestModel.ExitRequest;
import com.infosys.requestModel.EntryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.infosys.repository.CustomerRepository;
import com.infosys.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/gate")
public class GateController {

    @Autowired
    private GateService gateService;

    @Autowired
    private CustomerRepository customerRepository;


    @PostMapping("/create-customer")
    public ResponseEntity createEntry(@RequestParam("customerName") String customerName,
                                         @RequestParam("customerId") Long customerId){

        HashMap<String, Object> response = new HashMap<>();

        if(customerRepository.findById(customerId).isPresent()) {
            response.put("MESSAGE", "EXISTS");
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);

        }

        Customer entrySaved = customerRepository.save(new Customer(customerId, customerName));
        response.put("MESSAGE", "The Customer was created");
        return new ResponseEntity(response, HttpStatus.CREATED);

    }

    @PutMapping("/customer-entry")
    private ResponseEntity customerEntry(@RequestParam("collectionName") String collectionName,
                                         @RequestParam("customerId") Long customerId,
                                         @RequestParam("photos") List<MultipartFile> photos) {
        EntryRequest entryRequest = new EntryRequest(collectionName, customerId, new ArrayList<>());
        photos.stream().forEach(face -> entryRequest.getPhotos().add(face));

        try {
            boolean indexed =  gateService.processEntryRequest(entryRequest);
            if(!indexed)
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/customer-exit")
    private ResponseEntity customerExit(@RequestParam("collectionName") String collectionName,
                                        @RequestParam("customerId") Long customerId,
                                        @RequestParam("photos") List<MultipartFile> photos) {
        ExitRequest exitRequest = new ExitRequest(collectionName, customerId, photos);
        try {
            gateService.processExitRequest(exitRequest);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}