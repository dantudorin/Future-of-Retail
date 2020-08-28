package com.infosys.admin.service;

import com.infosys.admin.converter.Converter;
import com.infosys.admin.dto.StoreDTO;
import com.infosys.admin.exceptions.NotCreatedException;
import com.infosys.admin.model.Store;
import com.infosys.admin.repository.StoreRepository;
import com.infosys.admin.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    StoreRepository repository;

    @Autowired
    AwsService awsService;

    @Autowired
    Converter converter;


    public List<StoreDTO> findByPage(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize, Sort.by(sortBy));
        Page<Store> storePage = repository.findAll(pageable);
        List<Store> storeList= storePage.getContent();

        List<StoreDTO> storeDTOS = new ArrayList<>();

        storeList.stream().forEach( store -> {
            storeDTOS.add(converter.convertToDTO(store));
        });

        return storeDTOS;
    }


    public StoreDTO findStoreById(long id) throws NotFoundException {
        Optional<Store> optionalStore = repository.findById(id);

        if (optionalStore.isPresent()) {
            return converter.convertToDTO(optionalStore.get());
        }

        throw new NotFoundException("Store not found " + id);
    }


    public StoreDTO createStore(StoreDTO storeDTO) throws NotCreatedException {
        Integer status = awsService.createCollection(storeDTO.getName());
        if (HttpStatus.OK.value()!=status) {

            throw new NotCreatedException( "Store was not created!");
        }

        Store saved = repository.save(converter.convertToModel(storeDTO));
        return converter.convertToDTO(saved);
    }

    public void deleteStoreById(long id) {
        repository.deleteById(id);
    }
}