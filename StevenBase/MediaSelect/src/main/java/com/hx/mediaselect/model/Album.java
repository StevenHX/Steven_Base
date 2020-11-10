package com.hx.mediaselect.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * 专辑模型实体类
 */

public class Album {
    public ArrayList<AlbumItem> albumItems;
    private LinkedHashMap<String, AlbumItem> hasAlbumItems;//用于记录专辑项目

    public Album() {
        albumItems = new ArrayList<>();
        hasAlbumItems = new LinkedHashMap<>();
    }

    private void addAlbumItem(int i, AlbumItem albumItem) {
        this.hasAlbumItems.put(albumItem.name, albumItem);
        this.albumItems.add(i,albumItem);
    }

    public void addAlbumItem(String name, String folderPath, String coverImagePath, Uri coverImageUri) {
        if (null == hasAlbumItems.get(name)) {
            addAlbumItem(0,new AlbumItem(name, folderPath, coverImagePath,coverImageUri));
        }
    }

    public AlbumItem getAlbumItem(String name) {
        return hasAlbumItems.get(name);
    }

    public AlbumItem getAlbumItem(int currIndex) {
        return albumItems.get(currIndex);
    }

    public boolean isEmpty() {
        return albumItems.isEmpty();
    }

    public void clear() {
        albumItems.clear();
        hasAlbumItems.clear();
    }

    public ArrayList<AlbumItem> getAlbumItems() {
        return albumItems;
    }

    public void setAlbumItems(ArrayList<AlbumItem> albumItems) {
        this.albumItems = albumItems;
    }

    public LinkedHashMap<String, AlbumItem> getHasAlbumItems() {
        return hasAlbumItems;
    }

    public void setHasAlbumItems(LinkedHashMap<String, AlbumItem> hasAlbumItems) {
        this.hasAlbumItems = hasAlbumItems;
    }
}
