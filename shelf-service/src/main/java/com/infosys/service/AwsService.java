package com.infosys.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.infosys.config.ClientFactoryConfig;
import com.infosys.exceptions.CustomerNotFoundException;
import com.infosys.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;

@Service
public class AwsService {

    @Autowired
    private ClientFactoryConfig clientFactoryConfig;

    public Optional<Long> searchFace(String collectionName, MultipartFile userPhoto) throws CustomerNotFoundException {
        String message = "Customer not found";
        List<FaceMatch> foundedMatches = matchFace(collectionName, userPhoto);
        if (foundedMatches.size() == Helper.PERSON_FOUND) {
            return Optional.of(Long.parseLong(
                    foundedMatches.get(0).getFace().getExternalImageId()));
        }
        throw new CustomerNotFoundException(message);
    }

    public List<FaceMatch> matchFace(String collectionName, MultipartFile userPhoto){
        ByteBuffer byteBuffer = ByteBuffer.allocate(Helper.EMPTY);
        try {
            byteBuffer = ByteBuffer.wrap(userPhoto.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchFacesByImageRequest request = new SearchFacesByImageRequest()
                .withCollectionId(collectionName)
                .withImage(new Image().withBytes(byteBuffer));
        AmazonRekognition rekognition = clientFactoryConfig.getAmazonClient();
        SearchFacesByImageResult result = rekognition.searchFacesByImage(request);
        return result.getFaceMatches();
    }
}
