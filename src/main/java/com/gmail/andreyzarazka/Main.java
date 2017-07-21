package com.gmail.andreyzarazka;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.gmail.andreyzarazka.model.PhotoInfo;
import com.gmail.andreyzarazka.utils.NameComparator;
import com.gmail.andreyzarazka.utils.ShootingDateComparator;
import com.gmail.andreyzarazka.utils.SizeComparator;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;

import static com.gmail.andreyzarazka.utils.Extension.getFileExtension;

public class Main {
    final static String FLAG_FILE_MODIFIED_DATE = "File Modified Date";

    public static void main(String[] args) throws ImageProcessingException, IOException {
        Comparator<PhotoInfo> comparator = new ShootingDateComparator().thenComparing(new SizeComparator()).thenComparing(new NameComparator());
        TreeSet<PhotoInfo> photoInfos = new TreeSet<>(comparator);
        int count = 0;

        Path pathCurrentFolder = Paths.get("D:\\");
        DirectoryStream<Path> paths = Files.newDirectoryStream(pathCurrentFolder);
        for (Path path : paths) {
            // Исключаем каталоги и скрытые файлы
            if (path.toFile().isFile() && !path.toFile().isHidden()) {
                count++;

                Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());

                //Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
                ExifSubIFDDirectory dire = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                Date date;
                long shootingDate = 0L;
                if (dire != null) {
                    date = dire.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                    if (date != null) {
                        shootingDate = date.getTime();
                    } else {
                        for (Directory directory : metadata.getDirectories()) {
                            for (Tag tag : directory.getTags()) {
                                System.out.format("[%s] - %s = %s\n", directory.getName(), tag.getTagName(), tag.getDescription());
                                if (FLAG_FILE_MODIFIED_DATE == tag.getTagName()) {
                                    System.out.println("*****************************************************************");
                                    System.out.println(tag.getDescription());
                                }
                            }

//                            if (directory.hasErrors()) {
//                                for (String error : directory.getErrors()) {
//                                    System.err.format("ERROR: %s", error);
//                                }
//                            }
                        }
                    }
                }

                photoInfos.add(new PhotoInfo(shootingDate, path.toFile().length(), getFileExtension(path.getFileName().toString()), path.getFileName().toString(), path.getParent().toString()));
            }
        }

        int counterPhotos = 0;
        for (PhotoInfo photo : photoInfos) {
            counterPhotos++;
            Path currentPath = Paths.get(photo.getPath() + "\\" + photo.getName());
            Path newPath = Paths.get("D:\\tmp_foto\\" + "Photo_" + counterPhotos + photo.getExtension());
            Files.copy(currentPath, newPath, StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Копирование завершинно");
    }
}
