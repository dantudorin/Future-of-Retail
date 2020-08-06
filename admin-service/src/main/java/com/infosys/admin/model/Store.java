package com.infosys.admin.model;

import com.infosys.admin.dto.StoreDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Store {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "native")
    @GenericGenerator(name= "native", strategy = "native")
    private Long id;

    private String name;

    private String location;

    private Date createdAt;

    public Store() { }


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
}


