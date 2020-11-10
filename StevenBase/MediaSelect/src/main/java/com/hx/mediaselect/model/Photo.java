package com.hx.mediaselect.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {
    private String name;
    private Uri uri;
    private String path;
    private long dateTime;
    private int width;
    private int height;
    private long size;
    private int i;
    private String type;
    public boolean selected;

    public Photo() {
    }

    public Photo(String name, Uri uri, String path, long dateTime, int width, int height, long size, int i, String type) {
        this.name = name;
        this.uri = uri;
        this.path = path;
        this.dateTime = dateTime;
        this.width = width;
        this.height = height;
        this.size = size;
        this.i = i;
        this.type = type;
    }

    protected Photo(Parcel in) {
        name = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
        path = in.readString();
        dateTime = in.readLong();
        width = in.readInt();
        height = in.readInt();
        size = in.readLong();
        i = in.readInt();
        type = in.readString();
        selected = in.readByte() != 0;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "name='" + name + '\'' +
                ", uri=" + uri +
                ", path='" + path + '\'' +
                ", dateTime=" + dateTime +
                ", width=" + width +
                ", height=" + height +
                ", size=" + size +
                ", i=" + i +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeParcelable(uri, i);
        parcel.writeString(path);
        parcel.writeLong(dateTime);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeLong(size);
        parcel.writeInt(i);
        parcel.writeString(type);
        parcel.writeByte((byte) (selected ? 1 : 0));
    }
}
