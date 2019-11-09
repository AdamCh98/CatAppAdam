package com.example.catappadam;

import com.example.catappadam.Models.Cat;
import com.example.catappadam.Models.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class FakeDatabase {

    public static HashMap<String, Cat> cats = new HashMap<>();
    public static HashMap<String, Image> images = new HashMap<>();


    public static void saveCatsToFakeDatabase(ArrayList<Cat> catsToSave) {
        for(int i = 0; i < catsToSave.size(); i++) {
            Cat cat = catsToSave.get(i);
            cats.put(cat.getId(),cat);
        }
    }

    public static void saveImagesToFakeDatabase(ArrayList<Image> imagesToSave) {
        for(int i = 0; i < imagesToSave.size(); i++) {
            Image image = imagesToSave.get(i);
            images.put(image.getId(),image);
        }
    }

    public static Cat getCatById(String id) {return cats.get(id);}

    public static Image getImageById(String id) {return images.get(id);}
}
