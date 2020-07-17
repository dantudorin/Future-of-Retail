package com.infosys.admin.dto;

public class StoreDTO {
    String location;
    String name;
    Long id;

    public StoreDTO(String name, String location) {
        this.name = name;
        this.location = location;
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