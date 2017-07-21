package com.gmail.andreyzarazka.utils;

import com.gmail.andreyzarazka.model.PhotoInfo;

import java.util.Comparator;

/**
 * Created by Andrew Zarazka on 19.07.2017.
 */
public class NameComparator implements Comparator<PhotoInfo> {
    @Override
    public int compare(PhotoInfo photoOne, PhotoInfo photoTwo) {
        return photoOne.getName().compareTo(photoTwo.getName());
    }
}
