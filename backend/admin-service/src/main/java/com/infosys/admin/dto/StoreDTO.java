package com.infosys.admin.dto;

import com.infosys.admin.model.CustomerTracker;

public class StoreDTO {

    private CustomerTracker customerTracker;
    private String location;
    private String name;
    private Long id;

    public StoreDTO() { }

    public StoreDTO(String location, String name) {
        this.location = location;
        this.name = name;
        this.customerTracker = new CustomerTracker();
    }

    public StoreDTO(Long id, String name, String location, CustomerTracker customerTracker) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.customerTracker = customerTracker;
    }

    public StoreDTO(String name, String location, CustomerTracker customerTracker) {
        this.name = name;
        this.location = location;
        this.customerTracker = customerTracker;
    }

    public StoreDTO(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public CustomerTracker getCustomerTracker() {
        return customerTracker;
    }

    public void setCustomerTracker(CustomerTracker customerTracker) {
        this.customerTracker = customerTracker;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}