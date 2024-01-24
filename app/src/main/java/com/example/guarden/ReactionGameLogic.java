package com.example.guarden;
import java.util.Random;
public class ReactionGameLogic {
    private long startTime;
    private boolean isWaitingForClick = false;
    private Random random = new Random();

    public long startGame() {
        if (!isWaitingForClick) {
            startTime = System.currentTimeMillis();
            isWaitingForClick = true;
        }
        return startTime;
    }

    public long stopGame() {
        if (isWaitingForClick) {
            long reactionTime = System.currentTimeMillis() - startTime;
            isWaitingForClick = false;
            return reactionTime;
        }
        return -1;
    }

    public int randomColor() {
        return 0xFF000000 | random.nextInt(0xFFFFFF);
    }

    public boolean isWaitingForClick() {
        return isWaitingForClick;
    }
}