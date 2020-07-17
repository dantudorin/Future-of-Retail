package com.infosys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.infosys.collection.FaceCollection;
import org.springframework.http.HttpStatus;
import java.awt.Image;

@Controller
@RequestMapping("/entry")
public class GateController {
    @Autowired
    private FaceCollection faceCollection;

    @PutMapping
    public ResponseEntity<FaceCollection> updateCollection(@RequestBody Long id, Image image) {
        faceCollection.updateFaceCollection(image, id);
        return new ResponseEntity<FaceCollection>(HttpStatus.OK);
    }


    @DeleteMapping("/entry/{id}")
    public ResponseEntity<FaceCollection> deleteEntryById(@PathVariable(value = "id") Long id) {
        faceCollection.getFaceCollection().remove(id);
        return ResponseEntity.accepted().build();
    }

}
