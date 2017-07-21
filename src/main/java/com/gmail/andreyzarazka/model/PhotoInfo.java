package com.gmail.andreyzarazka.model;

import java.util.Objects;

public class PhotoInfo {
    private final long shootingDate;
    private final long size;
    private final String extension;
    private final String name;
    private final String path;

    public PhotoInfo(Long shootingDate, Long size, String extension, String name, String path) {
        this.shootingDate = shootingDate;
        this.size = size;
        this.extension = extension;
        this.name = name;
        this.path = path;
    }

    public Long getShootingDate() {
        return shootingDate;
    }

    public Long getSize() {
        return size;
    }

    public String getExtension() {
        return extension;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhotoInfo)) return false;
        PhotoInfo photoInfo = (PhotoInfo) o;
        return Objects.equals(getShootingDate(), photoInfo.getShootingDate()) &&
                Objects.equals(getSize(), photoInfo.getSize()) &&
                Objects.equals(getExtension(), photoInfo.getExtension()) &&
                Objects.equals(getName(), photoInfo.getName()) &&
                Objects.equals(getPath(), photoInfo.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShootingDate(), getSize(), getExtension(), getName(), getPath());
    }

    @Override
    public String toString() {
        return "PhotoInfo{" +
                "shootingDate=" + shootingDate +
                ", size=" + size +
                ", extension='" + extension + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
