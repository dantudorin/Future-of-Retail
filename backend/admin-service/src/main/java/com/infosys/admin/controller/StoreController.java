package com.infosys.admin.controller;

import com.amazonaws.services.rekognition.model.InvalidParameterException;
import com.amazonaws.services.rekognition.model.ResourceAlreadyExistsException;
import com.infosys.admin.dto.StoreDTO;
import com.infosys.admin.exceptions.NotFoundException;
import com.infosys.admin.model.Store;
import com.infosys.admin.service.AwsService;
import com.infosys.admin.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService service;

    @Autowired
    private AwsService awsService;


    @PostMapping
    public ResponseEntity<StoreDTO> createStore (@RequestBody StoreDTO storeDTO) throws Exception{
        try {
            StoreDTO response = service.createStore(storeDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ResourceAlreadyExistsException | InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);

        }
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity< List<StoreDTO>> getStoresByPage(
            @PathVariable(value = "pageNumber") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<StoreDTO> storesDto = service.findByPage(pageNo, pageSize, sortBy);
        ResponseEntity<List<StoreDTO>> responseEntity = new ResponseEntity<List<StoreDTO>>(storesDto, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable(value = "id") long id) {
        StoreDTO response;

        try {
            response = service.findStoreById(id);

        }catch (NotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/filter-stores")
    public ResponseEntity<List<StoreDTO>> getStoresBySearchedName(
            @RequestParam String storeName)
    {
        List<StoreDTO> storesDto = service.findAllByName(storeName);
        ResponseEntity<List<StoreDTO>> responseEntity = new ResponseEntity<List<StoreDTO>>(storesDto, HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Store> deleteStoreById(@PathVariable(value = "id") Long id) {
        service.deleteStoreById(id);

        return ResponseEntity.accepted().build();
    }
}