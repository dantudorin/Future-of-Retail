package com.infosys.controller;

import com.infosys.config.IdentifyActions;
import com.infosys.enums.CustomerAction;
import com.infosys.exceptions.CustomerNotFoundException;
import com.infosys.service.AwsService;
import com.infosys.service.ShelfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shelf")
public class ShelfController {

    @Autowired
    private AwsService awsService;

    @Autowired
    private ShelfService shelfService;

    @PostMapping("/event")
    public ResponseEntity shelfEvent(@RequestParam(value = "photo") List<MultipartFile> photos,
                                     @RequestParam(value = "storeName") String storeName) {
        try {
            Optional<Long> entity = awsService.searchFace (storeName, photos.get(0));
            CustomerAction action = shelfService.detectPutTake(photos, storeName);
            Logger log = LoggerFactory.getLogger("log");
            if(action != CustomerAction.NOT_ABLE_TO_IDENTIFY) {
                log.trace(action.toString());
            } else {
                log.warn(action.toString());
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch(CustomerNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getMessage(), e);
        }
    }
}
