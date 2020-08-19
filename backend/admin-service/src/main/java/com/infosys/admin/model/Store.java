package com.infosys.admin.model;

import com.infosys.admin.dto.StoreDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "native")
    @GenericGenerator(name= "native", strategy = "native")
    private Long id;

    private String name;

    private String location;

    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_tracker_id", referencedColumnName = "id")
    private CustomerTracker customerTracker;

    public Store() {
    }

    public Store(String name, String location, Date date) {
        this.name = name;
        this.location  = location;
        this.createdAt = date;
        this.customerTracker = new CustomerTracker();
    }

    public Store(String name, String location) {
        this.name = name;
        this.location  = location;
        this.customerTracker = new CustomerTracker();
    }

    public Store(String name, String location, CustomerTracker customerTracker) {
        this.name = name;
        this.location = location;
        this.customerTracker = customerTracker;
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