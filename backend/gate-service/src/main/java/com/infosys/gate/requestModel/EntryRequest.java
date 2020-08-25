package com.infosys.gate.requestModel;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class EntryRequest {

    private Long customerId;
    private String collectionName;
    private List<MultipartFile> photos;

    public EntryRequest(String collectionName, Long customerId, List<MultipartFile> photos){
        this.collectionName = collectionName;
        this.customerId = customerId;
        this.photos = photos;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }

}
