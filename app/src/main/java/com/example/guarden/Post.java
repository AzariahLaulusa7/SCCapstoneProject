package com.example.guarden;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

//Establishes the post item for use in the forum
public class Post {

    String name;
    String tag, key, user;
    String message;
    int image;
    int tagBackground;

    public Post() {}

    public Post(String name, String message, String tag, int image, int tagBackground, String key, String user) {
        this.name = name;
        this.message = message;
        this.tag = tag;
        this.image = image;
        this.tagBackground = tagBackground;
        this.key = key;
        this.user = user;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}