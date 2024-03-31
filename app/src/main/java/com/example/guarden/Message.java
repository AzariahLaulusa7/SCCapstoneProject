package com.example.guarden;

public class Message {
    private String user;
    private String message;

    public Message(){
        this.user = "user";
        this.message = "message";
    }

    public Message(String user, String message){
        this.user = user;
        this.message = message;
    }

    //Getters
    public String getUser(){
        return user;
    }

    public String getMessage(){
        return message;
    }

    //Setters

    public void setUser(String user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
