package com.infosys.admin.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.infosys.admin.config.CustomerFactoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwsService {


    @Autowired
    private CustomerFactoryConfig clientFactoryConfig;

    public Integer createCollection(String collectionName) {
        CreateCollectionRequest request = new CreateCollectionRequest()
                .withCollectionId(collectionName);

        System.out.println("Creating collection: " + collectionName );

        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();
        CreateCollectionResult result = rekognition.createCollection(request);

        System.out.println("Status code : " + result.getStatusCode().toString());
        return result.getStatusCode();
    }
}