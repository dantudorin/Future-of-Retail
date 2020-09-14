package com.infosys.service;

import com.infosys.config.IdentifyActions;
import com.infosys.enums.CustomerAction;
import com.infosys.exceptions.CustomerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfService {
    @Autowired
    private AwsService awsService;

    @Autowired
    private IdentifyActions detectIfCustomerHasObject;

    public CustomerAction detectPutTake(List<MultipartFile> photos, String storeName) throws CustomerNotFoundException {
        CustomerAction identificationProduct = detectIfCustomerHasObject.detectAction(photos);
        Logger log = LoggerFactory.getLogger("log");
        if(identificationProduct != CustomerAction.NOT_ABLE_TO_IDENTIFY) {
            log.trace(identificationProduct.toString());
        } else {
            log.warn(identificationProduct.toString());
        }
        return identificationProduct;
    }
    public Long identifyCustomer(List<MultipartFile> photos, String storeName) throws CustomerNotFoundException {
        Optional<Long> entity = awsService.searchFace (storeName, photos.get(0));
        Long customerId = entity.get();
        return customerId;
    }
}
