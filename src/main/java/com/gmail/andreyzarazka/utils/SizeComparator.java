package com.gmail.andreyzarazka.utils;

import com.gmail.andreyzarazka.model.PhotoInfo;

import java.util.Comparator;

/**
 * Created by Andrew Zarazka on 19.07.2017.
 */
public class SizeComparator implements Comparator<PhotoInfo> {
    @Override
    public int compare(PhotoInfo photoOne, PhotoInfo photoTwo) {
        if (photoOne.getSize() > photoTwo.getSize()) {
            return 1;
        } else if (photoOne.getSize() < photoTwo.getSize()) {
            return -1;
        } else {
            return 0;
        }
    }
}
