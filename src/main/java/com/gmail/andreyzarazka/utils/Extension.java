package com.gmail.andreyzarazka.utils;

/**
 * Created by Andrew Zarazka on 19.07.2017.
 */
public class Extension {
    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index == -1 ? null : fileName.substring(index);
    }
}
