package com.example.guarden;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
//Creates a comment object for use in the forum
public class Comment {

    String message, name;

    public Comment() {}

    public Comment(String message, String name) {
        this.message = message;
        this.name = name;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
