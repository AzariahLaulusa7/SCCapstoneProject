package com.example.guarden;

public class Pose {
    private String type;
    private String name;
    private int imageRes;
    private int like; //0=neutral,1=like,2=dislike
    public Pose(String type, String name, int imageRes){
        this.type = type;
        this.name = name;
        this.imageRes = imageRes;
        this.like = 0;
    }
    public String getType(){
        return this.type;
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
    public void setLike(int like){
        this.like = like;
    }
}
