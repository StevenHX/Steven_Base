package com.hx.mediaselect.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * 专辑项目实体类
 */

public class AlbumItem {
    public String name;
    public String folderPath;
    public String coverImagePath;
    public Uri coverImageUri;
    public ArrayList<Photo> photos;

    AlbumItem(String name, String folderPath, String coverImagePath, Uri coverImageUri) {
        this.name = name;
        this.folderPath = folderPath;
        this.coverImagePath = coverImagePath;
        this.coverImageUri = coverImageUri;
        this.photos = new ArrayList<>();
    }

    public void addImageItem(Photo imageItem) {
        this.photos.add(imageItem);
    }

    public void addImageItem(int index, Photo imageItem) {
        this.photos.add(index, imageItem);
    }

    public void addAllImageItem(List<Photo> items) {
        this.photos.addAll(items);
    }
}
