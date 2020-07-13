package com.infosys.admin.model;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import com.infosys.admin.dto.StoreDTO;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String location;
    private String name;
    private Date createdAt;

    protected Store() { }

    public Store(String name, String location, Date date) {
        this.name = name;
        this.location  = location;
        createdAt = date;
    }

    public Store(String name, String location) {
        this.name = name;
        this.location  = location;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public void setCreatedAt(Date createdAt) {

        this.createdAt = createdAt;
    }


    public Long getId() {

        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getLocation() {

        return location;
    }

    public Date getDate() {

        return createdAt;
    }

    public Store updateFromDTO(StoreDTO storeDto) {

        if (!(this.name.equals(storeDto.getName())))
            this.name = storeDto.getName();

        if (!(this.location.equals(storeDto.getLocation())))
            this.location = storeDto.getLocation();

        return this;
    }
}
