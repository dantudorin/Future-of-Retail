package com.infosys.gate.service;

import com.infosys.gate.model.Customer;
import com.infosys.gate.requestModel.EntryRequest;
import com.infosys.gate.requestModel.ExitRequest;
import com.infosys.admin.exceptions.NotFoundException;
import com.infosys.gate.exception.UserNotFoundException;
import com.infosys.gate.repository.CustomerRepository;
import com.infosys.gate.utils.AwsConstants;
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

    @Autowired
    private CustomerTrackerService customerTrackerService;

    public boolean processEntryRequest(EntryRequest entryRequest) throws UserNotFoundException, NotFoundException {
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

        customerTrackerService.updateCustomerNumber(entryRequest.getCollectionName(), AwsConstants.ONE_CUSTOMER);

        return true;
    }

    public void processExitRequest (ExitRequest exitRequest) throws UserNotFoundException, NotFoundException {
            Customer customer = customerRepository.findById(exitRequest.getCustomerId())
                    .orElseThrow(() -> new UserNotFoundException(exitRequest.getCustomerId()));

           awsService.removeFromCollection(exitRequest.getCollectionName(), exitRequest.getCustomerId());
           customerTrackerService.decrementCustomers(exitRequest.getCollectionName(), AwsConstants.ONE_CUSTOMER);
    }
}
