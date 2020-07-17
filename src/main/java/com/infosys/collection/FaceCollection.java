package com.infosys.collection;

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;

public class FaceCollection {

    private HashMap<Long, Collection<Image>> faceCollection = new HashMap<Long, Collection<Image>>();

    public void updateFaceCollection (Image image, Long id) {
        if(faceCollection.containsKey(id)) {
            faceCollection.get(id).add(image);
        }else {
            Collection<Image> collection = new ArrayList<Image>();
            collection.add(image);
            faceCollection.put(id, collection);
        }
    }

    public HashMap<Long, Collection<Image>> getFaceCollection(){
        return faceCollection;
    }
}
