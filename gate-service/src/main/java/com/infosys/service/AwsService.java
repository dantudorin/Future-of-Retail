package com.infosys.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.infosys.config.CustomerFactoryConfig;
import com.infosys.utils.CollectionDetails;
import com.infosys.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AwsService {

    @Autowired
    private CustomerFactoryConfig clientFactoryConfig;

    public List getCollections() {
        ListCollectionsRequest request = new ListCollectionsRequest();
        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();
        ListCollectionsResult result = rekognition.listCollections(request);

        List collectionIds = result.getCollectionIds();

        return collectionIds;
    }

    public Integer createCollection(String collectionName) {
        CreateCollectionRequest request = new CreateCollectionRequest()
                .withCollectionId(collectionName);

        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();
        CreateCollectionResult result = rekognition.createCollection(request);

        return result.getStatusCode();
    }

    public Integer deleteCollection(String collectionName) {
        DeleteCollectionRequest request = new DeleteCollectionRequest()
                .withCollectionId(collectionName);
        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();
        DeleteCollectionResult result = rekognition.deleteCollection(request);

        return result.getStatusCode();
    }

    public Optional<String> indexFace(String collectionName, MultipartFile customerPhoto, String photoReconId) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Helper.EMPTY);
        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();

        try {
            byte[] bytePhoto = customerPhoto.getBytes();
            byteBuffer = ByteBuffer.wrap(bytePhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        IndexFacesRequest request = new IndexFacesRequest()
                .withCollectionId(collectionName)
                .withDetectionAttributes(Helper.ALL)
                .withImage(new Image().withBytes(byteBuffer))
                .withExternalImageId(photoReconId);

        IndexFacesResult result = rekognition.indexFaces(request);
        return result.getFaceRecords().stream().map(faceRecord -> faceRecord.getFace()
                    .getFaceId()).findFirst();
    }


    public List<FaceMatch> matchFace(String collectionName, MultipartFile userPhoto) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Helper.EMPTY);

        try {
            byteBuffer = ByteBuffer.wrap(userPhoto.getBytes());
        } catch (Exception e) {
            System.out.println(e);
        }

        SearchFacesByImageRequest request = new SearchFacesByImageRequest()
                .withCollectionId(collectionName)
                .withImage(new Image().withBytes(byteBuffer));

        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();
        SearchFacesByImageResult result = rekognition.searchFacesByImage(request);

        return result.getFaceMatches();
    }

    public HashMap getCollectionDetails(String collectioName) {
        DescribeCollectionRequest request = new DescribeCollectionRequest()
                .withCollectionId(collectioName);
        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();
        DescribeCollectionResult result = rekognition.describeCollection(request);

        HashMap<String, String> payload = new HashMap<>();

        payload.put(CollectionDetails.ARN, result.getCollectionARN());
        payload.put(CollectionDetails.CREATED_AT, String.valueOf(result.getCreationTimestamp()));
        payload.put(CollectionDetails.FACE_MODEL_VERSION, result.getFaceModelVersion());
        payload.put(CollectionDetails.FACE_COUNT, String.valueOf(result.getFaceCount()));

        return payload;
    }

    public void removeFromCollection(String collectionName, List<String> faceIds) {
        AmazonRekognition amazonRekognition = clientFactoryConfig.getAmazonClient();
        DeleteFacesRequest request = new DeleteFacesRequest()
                .withCollectionId(collectionName)
                .withFaceIds(faceIds);

        amazonRekognition.deleteFaces(request);
    }
}
