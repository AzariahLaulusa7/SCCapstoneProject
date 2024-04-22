package com.example.guarden;

public class Message {

    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;
    private int viewType;
    private String message;

    public Message(){
        this.viewType = LayoutOne;
        this.message = "message";
    }

    public Message(int viewType, String message){
        this.viewType = viewType;
        this.message = message;
    }

    //Getters
    public int getViewType(){
        return viewType;
    }

    public String getMessage(){
        return message;
    }

    //Setters

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
