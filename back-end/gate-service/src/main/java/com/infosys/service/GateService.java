package com.infosys.service;

import com.infosys.exception.UserNotFoundException;
import com.infosys.model.Customer;
import com.infosys.repository.CustomerRepository;
import com.infosys.requestModel.EntryRequest;
import com.infosys.requestModel.ExitRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Component
public class GateService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AwsService awsService;

    public boolean processEntryRequest(EntryRequest entryRequest) throws UserNotFoundException {
        Customer customer = customerRepository.findById(entryRequest.getCustomerId())
                .orElseThrow(() ->new UserNotFoundException(entryRequest.getCustomerId()));

        entryRequest.getPhotos().stream().forEach(photo -> {
            boolean indexed = false;
            try {
                indexed = awsService.indexFace(entryRequest.getCollectionName(), photo,
                        entryRequest.getCustomerId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return true;
    }

    public void processExitRequest (ExitRequest exitRequest) throws UserNotFoundException {
            Customer customer = customerRepository.findById(exitRequest.getCustomerId())
                    .orElseThrow(() -> new UserNotFoundException(exitRequest.getCustomerId()));

            awsService.removeFromCollection(exitRequest.getCollectionName(), exitRequest.getCustomerId());
    }
}
