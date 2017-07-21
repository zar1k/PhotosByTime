package com.gmail.andreyzarazka.utils;

import com.gmail.andreyzarazka.model.PhotoInfo;

import java.util.Comparator;

/**
 * Created by Andrew Zarazka on 19.07.2017.
 */
public class ShootingDateComparator implements Comparator<PhotoInfo> {
    @Override
    public int compare(PhotoInfo photoOne, PhotoInfo photoTwo) {
        if (photoOne.getShootingDate() > photoTwo.getShootingDate()) {
            return 1;
        } else if (photoOne.getShootingDate() < photoTwo.getShootingDate()) {
            return -1;
        } else {
            return 0;
        }
    }
}
