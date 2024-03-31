package com.example.guarden;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Post implements Serializable {

    String name;
    String tag;
    String message;
    int image;
    int tagBackground;

    public Post() {}

    public Post(String name, String message, String tag, int image, int tagBackground) {
        this.name = name;
        this.message = message;
        this.tag = tag;
        this.image = image;
        this.tagBackground = tagBackground;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTagBackground() {
        return tagBackground;
    }

    public void setTagBackground(int tagBackground) {
        this.tagBackground = tagBackground;
    }
}