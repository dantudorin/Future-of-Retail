package com.infosys.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.infosys.admin.exceptions.NotFoundException;
import com.infosys.admin.repository.StoreRepository;
import org.springframework.stereotype.Service;
import com.infosys.admin.converter.Converter;
import com.infosys.admin.dto.StoreDTO;
import com.infosys.admin.model.Store;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    StoreRepository repository;

    @Autowired
    Converter converter;

    public StoreDTO add(StoreDTO storeDto) {
        Store save = repository.save(converter.convertToModel(storeDto));

        return converter.convertToDTO(save);
    }

    public List<StoreDTO> findAll() {
        List<Store> storeList = repository.findAll();
        List<StoreDTO> storeDTOS = new ArrayList<>();

        for (Store s : storeList)
            storeDTOS.add(new StoreDTO(s.getName(), s.getLocation()));

        return storeDTOS;
    }


    public StoreDTO findStoreById(long id) throws NotFoundException {
        Optional<Store> optionalStore = repository.findById(id);

        if (optionalStore.isPresent()) {
            return new StoreDTO(optionalStore.get().getName(), optionalStore.get().getLocation());
        }

        throw new NotFoundException("Store not found "+ id);
    }

    public StoreDTO updateStore(StoreDTO storeDTO) throws NotFoundException {
        Optional<Store> optionalStore = repository.findById(storeDTO.getId());

        if (!optionalStore.isPresent()) {
            throw new NotFoundException("Nu exista un store cu id " + storeDTO.getId());
        }

        Store s = optionalStore.get().updateFromDTO(storeDTO);
        Store saved = repository.save(s);
        return converter.convertToDTO(saved);
    }

    public void deleteStoreById(long id) {
        repository.deleteById(id);
    }
}
