package com.gmail.andreyzarazka;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.*;
import com.drew.metadata.file.FileMetadataDirectory;
import com.gmail.andreyzarazka.model.PhotoInfo;
import com.gmail.andreyzarazka.utils.NameComparator;
import com.gmail.andreyzarazka.utils.ShootingDateComparator;
import com.gmail.andreyzarazka.utils.SizeComparator;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;

import static com.gmail.andreyzarazka.utils.FileUtil.formatNumber;
import static com.gmail.andreyzarazka.utils.FileUtil.getFileExtension;
import static com.gmail.andreyzarazka.utils.FileUtil.getSupportedExtensions;

public class Main {
    public static void main(String[] args) throws ImageProcessingException, IOException {
        String supportedExtensions = getSupportedExtensions();
        Comparator<PhotoInfo> comparator = new ShootingDateComparator().thenComparing(new SizeComparator()).thenComparing(new NameComparator());
        TreeSet<PhotoInfo> photoInfos = new TreeSet<>(comparator);
        int count = 0;

        Path pathCurrentFolder = Paths.get("D:\\tmp");
        DirectoryStream<Path> paths = Files.newDirectoryStream(pathCurrentFolder, supportedExtensions);
        for (Path path : paths) {
            // Исключаем каталоги и скрытые файлы
            if (path.toFile().isFile() && !path.toFile().isHidden()) {
                count++;

                Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
                ExifDirectoryBase exifIFD0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                ExifDirectoryBase exifSub = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                Directory directory = metadata.getFirstDirectoryOfType(FileMetadataDirectory.class);

                Date dateOriginal;
                long timestamp;

                if (exifSub != null) {
                    dateOriginal = exifSub.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                    if (dateOriginal != null) {
                        timestamp = dateOriginal.getTime();
                    } else {
                        timestamp = exifIFD0.getDate(ExifIFD0Directory.TAG_DATETIME).getTime();
                    }
                } else {
                    timestamp = directory.getDate(FileMetadataDirectory.TAG_FILE_MODIFIED_DATE).getTime();
                }
                photoInfos.add(new PhotoInfo(timestamp, path.toFile().length(), getFileExtension(path.getFileName().toString()), path.getFileName().toString(), path.getParent().toString()));
            }
        }

        System.out.println(count + " файла(ов).");

        int counterPhotos = 0;
        for (PhotoInfo photo : photoInfos) {
            counterPhotos++;
            Path currentPath = Paths.get(photo.getPath() + "\\" + photo.getName());
            Path newPath = Paths.get("D:\\tmp_foto\\" + "NAME" + formatNumber(count, counterPhotos) + photo.getExtension());
            Files.copy(currentPath, newPath, StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Копирование завершинно. Скопировано " + counterPhotos + " файла(ов).");
    }
}
