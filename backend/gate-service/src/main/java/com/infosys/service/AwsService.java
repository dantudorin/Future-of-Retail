package com.infosys.service;

import java.util.List;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;
import com.infosys.utils.AwsConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.infosys.config.CustomerFactoryConfig;
import org.springframework.stereotype.Component;
import com.amazonaws.services.rekognition.model.*;
import com.infosys.exception.FaceNotIndexedException;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.rekognition.AmazonRekognition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

@Service
@Component
public class AwsService {

    @Autowired
    private CustomerFactoryConfig clientFactoryConfig;

    public AwsService(CustomerFactoryConfig clientFactoryConfig) {
        this.clientFactoryConfig = clientFactoryConfig;
    }

    public boolean indexFace(String collectionName, MultipartFile customerPhoto, Long customerId) throws IOException{
        ByteBuffer byteBuffer = ByteBuffer.allocate(AwsConstants.EMPTY);
        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();

        try {
            byte[] bytePhoto = customerPhoto.getBytes();
            byteBuffer = ByteBuffer.wrap(bytePhoto);
        } catch (IOException e) {
            throw new IOException("Not permitted");
        }

        IndexFacesRequest request = new IndexFacesRequest()
                .withCollectionId(collectionName)
                .withDetectionAttributes(AwsConstants.ALL)
                .withImage(new Image().withBytes(byteBuffer))
                .withExternalImageId(customerId.toString());

        IndexFacesResult result = rekognition.indexFaces(request);

        if ((result.getFaceRecords().size() == 1) && (result.getUnindexedFaces().isEmpty())) {
            return true;
        } else{
            FaceNotIndexedException e = new FaceNotIndexedException();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


    public List<FaceMatch> matchFace(String collectionName, MultipartFile userPhoto) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(AwsConstants.EMPTY);

        try {
            byteBuffer = ByteBuffer.wrap(userPhoto.getBytes());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        SearchFacesByImageRequest request = new SearchFacesByImageRequest()
                .withCollectionId(collectionName)
                .withImage(new Image().withBytes(byteBuffer));

        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();
        SearchFacesByImageResult result = rekognition.searchFacesByImage(request);
        return result.getFaceMatches();
    }

    public void removeFromCollection(String collectionName, Long customerId) {

        AmazonRekognition amazonRekognition = clientFactoryConfig.getAmazonClient();
        ListFacesRequest request = new ListFacesRequest().withCollectionId(collectionName).withMaxResults(1000);
        ListFacesResult response = amazonRekognition.listFaces(request);

        List<Face> toDelete = response.getFaces().stream().filter(element-> element.getExternalImageId()
                .equals(customerId.toString())).collect(Collectors.toList());

        DeleteFacesRequest deleteFacesRequest = new DeleteFacesRequest()
                    .withCollectionId(collectionName).withFaceIds(toDelete.stream()
                    .map(faces->faces.getFaceId()).collect(Collectors.toList()));
        DeleteFacesResult deleteFacesResult = amazonRekognition.deleteFaces(deleteFacesRequest);
    }
}
