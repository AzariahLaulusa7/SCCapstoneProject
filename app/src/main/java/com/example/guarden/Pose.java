package com.example.guarden;

public class Pose {
    private String type;
    private String name;
    private int imageRes;
    public Pose(String type, String name, int imageRes){
        this.type = type;
        this.name = name;
        this.imageRes = imageRes;
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
}
