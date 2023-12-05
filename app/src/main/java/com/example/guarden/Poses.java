package com.example.guarden;
import java.util.*;

public class Poses extends ArrayList{
    private String type;

    public Poses(String type) {
        this.type = type;
        if (type.equals("yoga")) {
            this.add(R.drawable.pose1);
            this.add(R.drawable.pose2);
            this.add(R.drawable.pose3);
        } else if (type.equals("exercise")) {
        }
    }
    public void changeType(String type){
        this.type=type;
        //create new instance of Poses
    }
}
