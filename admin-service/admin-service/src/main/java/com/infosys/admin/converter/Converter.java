package com.infosys.admin.converter;

import com.infosys.admin.dto.StoreDTO;
import com.infosys.admin.model.Store;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class Converter {

    public Store convertToModel(StoreDTO storeDto) {
        Store store = new Store(storeDto.getName(), storeDto.getLocation());
        store.setId(storeDto.getId());

        return store;
    }

    public StoreDTO convertToDTO(Store store) {
        StoreDTO storeDto = new StoreDTO(store.getName(), store.getLocation());
        storeDto.setId(store.getId());

        return storeDto;
    }
}