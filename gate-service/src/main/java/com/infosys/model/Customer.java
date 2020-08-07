package com.infosys.model;

import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "customer_group_id", unique = true)
    private String groupId;


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Face> storedFaces;

    public Customer() {
    }

    public Customer(Long id, String name, String groupId, List<Face> storedFaces) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.storedFaces = storedFaces;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Face> getStoredFaces() {
        return storedFaces;
    }

    public void setStoredFaces(List<Face> storedFaces) {
        this.storedFaces = storedFaces;
    }
}
