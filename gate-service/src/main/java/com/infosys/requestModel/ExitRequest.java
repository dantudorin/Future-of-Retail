package com.infosys.requestModel;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class ExitRequest {

    private Long customerId;
    private String collectionName;
    private List<MultipartFile> photos;

    public ExitRequest(String collectionName, Long customerId, List<MultipartFile> photos){
        this.collectionName = collectionName;
        this.customerId = customerId;
        this.photos = photos;
    }

    public String getCollectionName() {

        return collectionName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

}
