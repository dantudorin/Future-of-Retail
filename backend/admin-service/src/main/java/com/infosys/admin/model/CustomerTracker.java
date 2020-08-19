package com.infosys.admin.model;

import com.infosys.admin.utils.StoreConstants;

import javax.persistence.*;

@Entity
public class CustomerTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int nowInStore;

    private int totalOfTheDay;

    private int allTimeCustomers;

    public CustomerTracker() {
        this.allTimeCustomers = StoreConstants.EMPTY;
        this.nowInStore = StoreConstants.EMPTY;
        this.totalOfTheDay = StoreConstants.EMPTY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNowInStore() {
        return nowInStore;
    }

    public void setNowInStore(int nowInStore) {
        this.nowInStore = nowInStore;
    }

    public int getTotalOfTheDay() {
        return totalOfTheDay;
    }

    public void setTotalOfTheDay(int totalOfTheDay) {
        this.totalOfTheDay = totalOfTheDay;
    }

    public int getAllTimeCustomers() {
        return allTimeCustomers;
    }

    public void setAllTimeCustomers(int allTimeCustomers) {
        this.allTimeCustomers = allTimeCustomers;
    }
}
