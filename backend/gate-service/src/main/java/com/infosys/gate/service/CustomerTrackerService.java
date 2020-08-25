package com.infosys.gate.service;

import com.infosys.admin.exceptions.NotFoundException;
import com.infosys.admin.model.CustomerTracker;
import com.infosys.admin.model.Store;
import com.infosys.admin.repository.CustomerTrackerRepository;
import com.infosys.admin.repository.StoreRepository;
import com.infosys.gate.utils.AwsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerTrackerService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerTrackerRepository customerTrackerRepository;

    public void updateCustomerNumber(String storeName, int customers) throws NotFoundException {
        Store store = storeRepository.findByName(storeName)
                .orElseThrow(() -> new NotFoundException(AwsConstants.STORE_NOT_FOUND));

        CustomerTracker customerTracker = store.getCustomerTracker();
        customerTracker.setNowInStore(customerTracker.getNowInStore() + customers);
        customerTracker.setTotalOfTheDay(customerTracker.getTotalOfTheDay() + customers);
        customerTracker.setAllTimeCustomers(customerTracker.getAllTimeCustomers() + customers);

        customerTrackerRepository.save(customerTracker);
    }

    public void decrementCustomers(String storeName, int customersWhoExits) throws NotFoundException {
        Store store = storeRepository.findByName(storeName)
                .orElseThrow(() -> new NotFoundException(AwsConstants.STORE_NOT_FOUND));
        store.getCustomerTracker().setNowInStore(store.getCustomerTracker().getNowInStore() - customersWhoExits);
        storeRepository.save(store);
    }
}
