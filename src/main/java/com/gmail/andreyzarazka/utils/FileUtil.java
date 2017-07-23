package com.gmail.andreyzarazka.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.lang.String.format;

/**
 * Created by Andrew Zarazka on 19.07.2017.
 */
public class FileUtil {
    private static final Set<String> supportedExtensions = new LinkedHashSet<>(Arrays.asList(
            "jpg", "jpeg", "png", "gif", "bmp", "ico", "webp", "pcx", "ai", "eps",
            "nef", "crw", "cr2", "orf", "arw", "raf", "srw", "x3f", "rw2", "rwl",
            "tif", "tiff", "psd", "dng"));

    public static String getSupportedExtensions() {
        String extensions = supportedExtensions.toString();
        String res = extensions.replace("[", "*{").replace("]", "}");
        return res;
    }

    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index == -1 ? null : fileName.substring(index);
    }

    public static String formatNumber(int max, int number) {
        int size = getCountsOfDigits(max);
        String str = "_%0" + size + "d";
        return format(str, number);
    }

    private static int getCountsOfDigits(int number) {
        return (number == 0) ? 1 : (int) Math.ceil(Math.log10(Math.abs(number) + 0.5));
    }
}
