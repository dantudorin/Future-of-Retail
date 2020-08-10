package com.infosys.controller;

import com.amazonaws.services.rekognition.model.*;
import com.infosys.exception.FaceNotExistsException;
import com.infosys.exception.UserNotFoundException;
import com.infosys.model.Customer;
import com.infosys.model.Face;
import com.infosys.repository.CustomerRepository;
import com.infosys.repository.FaceRepository;
import com.infosys.service.AwsService;
import com.infosys.utils.Helper;
import com.infosys.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.infosys.collection.FaceCollection;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/entry")
public class GateController {

    private FaceCollection faceCollection;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FaceRepository faceRepository;

    @Autowired
    private AwsService awsService;

    @GetMapping("/get-collections")
    private ResponseEntity getCollections() {
        HashMap <String, Object> response = new HashMap<>();
        List collections = awsService.getCollections();

        if(collections.size() == Helper.EMPTY) {
            response.put(Response.MESSAGE, Response.EMPTY_LIST);
            return new ResponseEntity(response, HttpStatus.OK);
        }

        response.put(Response.PAYLOAD, collections);
        return new ResponseEntity(response, HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<FaceCollection> updateCollection(@RequestBody Long id, Image image) {
        faceCollection.updateFaceCollection(image, id);
        return new ResponseEntity<FaceCollection>(HttpStatus.OK);
    }

    @PostMapping("/upload-rekognition-photos")
    private ResponseEntity uploadCustomerPhotos(@RequestParam("collectionName") String collectionName,
                                                @RequestParam("groupId") String groupId,
                                                @RequestParam("customerId") Long customerId,
                                                @RequestParam("photos") List<MultipartFile> customerPhoto) {
        Customer customer;
        try {
            customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new UserNotFoundException());
            for(MultipartFile photo: customerPhoto){
                String faceId = awsService.indexFace(collectionName, photo, groupId)
                        .orElseThrow(() -> new FaceNotExistsException());
                Face face = new Face(faceId, customer);
                faceRepository.save(face);
            }
        } catch (UserNotFoundException | FaceNotExistsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return null;
    }

    @DeleteMapping("/entry/{id}")
    public ResponseEntity<FaceCollection> deleteEntryById(@PathVariable(value = "id") Long id) {
        faceCollection.getFaceCollection().remove(id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete-customer")
    public void deleteCustomer(@RequestParam(value = "groupId", required = true) String groupId,
                               @RequestParam(value = "collectioname", required = true) String collectionName) {
        try {
            Customer customer = customerRepository.findByGroupId(groupId)
                    .orElseThrow(() -> new UserNotFoundException());

            awsService.removeFromCollection(collectionName,
                    customer.getStoredFaces()
                            .stream()
                            .map(face -> face.getFaceId())
                            .collect(Collectors.toList())
            );

            customerRepository.delete(customer);

        } catch (UserNotFoundException |
                InvalidParameterException |
                AccessDeniedException |
                InternalServerErrorException |
                ResourceNotFoundException e) {

            e.printStackTrace();

        }
    }

}
