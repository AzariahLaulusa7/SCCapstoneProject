package com.example.guarden;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReactionGameLogicTest {

    private ReactionGameLogic gameLogic;

    @Before
    public void setUp() {
        gameLogic = new ReactionGameLogic();
    }

    @Test
    public void testStopGameWithoutStarting() {
        // Calling stopGame without starting the game
        long reactionTime = gameLogic.stopGame();

        // Expecting -1 since the game was not started
        assertEquals("Reaction time should be -1 if the game has not started", -1, reactionTime);
    }
}
