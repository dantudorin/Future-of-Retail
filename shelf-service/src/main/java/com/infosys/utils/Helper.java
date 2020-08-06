package com.infosys.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Helper {

    public static final int MIN_PHOTOS = 4;
    public static final int SIMILARITY_COEF = 75;
    public static final int EMPTY = 0;
    public static final String JPG_FORMAT = ".jpg";
    public static final String PNG_FORMAT = ".png";
    public static final String ALL = "ALL";
    public static final int PERSON_FOUND = 1;
    public static final int FIRST_ELEMENT = 0;

    public static ResponseEntity checkCollection(List collections, String collectionName) {
        HashMap<String, Object> response = new HashMap<>();

        if(collections.size() == Helper.EMPTY) {
            response.put(Response.MESSAGE, Response.COULD_NOT_INSERT);
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        Optional presentCollection = collections.stream()
                .filter(collection -> collection.equals(collectionName))
                .findFirst();

        if(!presentCollection.isPresent()) {
            response.put(Response.MESSAGE, Response.NOT_FOUND);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
