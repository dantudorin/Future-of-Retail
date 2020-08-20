package com.infosys.admin.config;

import com.infosys.admin.model.CustomerTracker;
import com.infosys.admin.model.Store;
import com.infosys.admin.repository.CustomerTrackerRepository;
import com.infosys.admin.utils.StoreConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class CustomerTrackingSchedulerConfig {

    @Autowired
    private CustomerTrackerRepository customerTrackerRepository;

    @Scheduled(fixedRate = StoreConstants.ONE_MINUTE)
    public void resetCustomersAsync() {
        List<CustomerTracker> customerTrackers = customerTrackerRepository.findAll();
        customerTrackers.forEach(customerTracker -> {
            customerTracker.setNowInStore(StoreConstants.EMPTY);
            customerTracker.setTotalOfTheDay(StoreConstants.EMPTY);
        });
        customerTrackerRepository.saveAll(customerTrackers);
    }
}
