package com.example.guarden;

public class Pose {
    private String category;
    private String name;
    private int imageRes;
    private String description;
    private int like; //0=neutral,1=like,2=dislike
    public Pose(String category, String name, Integer imageRes, String description){
        this.category = category;
        this.name = name;
        this.imageRes = imageRes;
        this.description = description;
        this.like = 0;
    }
    public String getCategory(){
        return this.category;
    }
    public String getName(){
        return this.name;
    }
    public int getImageRes(){
        return this.imageRes;
    }
    public int getLike(){
        return like;
    }
    public String getDescription(){
        return description;
    }
    public void setLike(int like){
        this.like = like;
    }
}
