package com.infosys.admin.controller;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import com.infosys.admin.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.infosys.admin.service.StoreService;
import org.springframework.http.HttpStatus;
import com.infosys.admin.dto.StoreDTO;
import com.infosys.admin.model.Store;
import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService service;

    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDto) {
        StoreDTO response = service.add(storeDto);

        return new ResponseEntity<StoreDTO>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<StoreDTO> storesDto = service.findAll();
        ResponseEntity<List<StoreDTO>> responseEntity = new ResponseEntity<List<StoreDTO>>(storesDto, HttpStatus.FOUND);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable(value = "id") long id) {
        StoreDTO response;
        try {
            response = service.findStoreById(id);
        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return new ResponseEntity<StoreDTO>(response, HttpStatus.FOUND);
    }

    @PutMapping
    public ResponseEntity<StoreDTO> updateStore(@RequestBody StoreDTO storeDtoDetails) throws NotFoundException {
        StoreDTO response = service.updateStore(storeDtoDetails);

        return new ResponseEntity<StoreDTO>(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Store> deleteStoreById(@PathVariable(value = "id") Long id) {
        service.deleteStoreById(id);

        return ResponseEntity.accepted().build();
    }
}