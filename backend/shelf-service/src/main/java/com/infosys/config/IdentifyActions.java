package com.infosys.config;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;
import com.infosys.enums.CustomerAction;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;

public class IdentifyActions {
    /* function that verifies if there are at least 2 labels that have instances and they are
    * either Beverage/Person or have as parent Beverage/Person. That is why it should iterate through
    * all labels that have at least one instance and are either named Beverage/Person or have, as a parent,
    * Beverage/Person. */
    private boolean detectInstancePersonOrBeverage(List<Label> labels) {
        String BEVERAGE = "Beverage";
        String PERSON = "Person";
        final int[] sa = {0};
        for (Label label : labels) {
            if (!label.getInstances().isEmpty()) {
                if (label.getName().equals(BEVERAGE) || label.getName().equals(PERSON)) {
                    sa[0]++;
                    if (sa[0] == 2) {
                        return true;
                    }
                }
                if (!label.getParents().isEmpty()) {
                    for (Parent parent : label.getParents()) {
                        if (parent.getName().equals(BEVERAGE) || parent.getName().equals(PERSON)) {
                            sa[0]++;
                            if (sa[0] == 2) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /* function that takes a MultipartFile and using an AmazonRekognition object and a request,
    * will obtain a list of labels for that photo. After calling detectInstancePersonOrBeverage,
    * we will know, based on the labels, if the customer took or put a product from/on the shelf.
    * */
    private boolean getInformationFromPhoto(MultipartFile photo) {
        ByteBuffer imageBytes;
        boolean information = false;
        try (InputStream inputStream = new FileInputStream(new File(Objects.requireNonNull(photo.getOriginalFilename())))) {
            ClientFactoryConfig clientFactoryConfig = new ClientFactoryConfig();
            AmazonRekognition rekognitionClient = clientFactoryConfig.getAmazonClient();
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
            DetectLabelsRequest request = new DetectLabelsRequest()
                    .withImage(new Image().withBytes(imageBytes))
                    .withMaxLabels(10);
            DetectLabelsResult result = rekognitionClient.detectLabels(request);

            List<Label> labels = result.getLabels();
            information = detectInstancePersonOrBeverage(labels);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return information;
    }
    /* takes a list of photos taken when a client approaches a shelf, compares the first
    * and the last one and returns whether a product was taken from or put on the
    * shelf by a specific client */
    public CustomerAction detectAction(List<MultipartFile> photos) {
        boolean beginning, end;
        beginning = getInformationFromPhoto(photos.get(0));
        end = getInformationFromPhoto(photos.get(photos.size() - 1));
        if (beginning && !end) {
            return CustomerAction.PUT;
        }
        if (!beginning && end) {
            return CustomerAction.TAKE;
        }
        return CustomerAction.NOT_ABLE_TO_IDENTIFY;
    }
}
