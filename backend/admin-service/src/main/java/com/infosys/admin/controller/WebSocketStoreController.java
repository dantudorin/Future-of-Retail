package com.infosys.admin.controller;

import com.infosys.admin.dto.PaginationDataDTO;
import com.infosys.admin.dto.StoreDTO;
import com.infosys.admin.repository.StoreRepository;
import com.infosys.admin.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketStoreController {

    @Autowired
    private StoreService storeService;

    @MessageMapping("/update")
    @SendTo("/topic/updatecustomers")
    public List<StoreDTO> getAllStores(PaginationDataDTO paginationData) {
       return storeService.findAll(paginationData.getPageNo(), paginationData.getPageSize(), paginationData.getSortBy());
    }
}
