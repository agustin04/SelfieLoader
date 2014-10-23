package com.sergio_agustin.selfieloader;

/**
 * Created by Sergio on 23/10/2014.
 */
public class Image {

    private String url;
    private int width;
    private int height;
    private ImageType type;

    public enum ImageType{
        LOW_RES,
        STD_RES,
        THUMB
    }
    
    public Image(){
        url = null;
    }

    public Image(String url, int width, int height, ImageType type) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }
}

