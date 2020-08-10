package com.infosys.model;

import javax.persistence.*;

@Entity
public class Face {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "face_id", unique = true)
    private String faceId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    protected Face() {
    }
    public Face(Long id, String faceID) {
        this.id = id;
        this.faceId = faceID;
    }

    public Face(String faceId, Customer customer) {
        this.faceId = faceId;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
