package com.example.guarden;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChatbotBrainTest {

    @Test
    public void testGetResponse() {
        // Test case 1: Valid input, expecting a response
        String userInput1 = "I feel anxious";
        String response1 = ChatbotBrain.getResponse(userInput1);
        assertNotNull(response1);
        assertTrue(response1.length() > 0);

        // Test case 2: Empty input, expecting a response
        String userInput2 = "";
        String response2 = ChatbotBrain.getResponse(userInput2);
        assertNotNull(response2);
        assertTrue(response2.length() > 0);
    }

    @Test
    public void testPreprocessText() {
        // Test case 1: Valid input
        String input1 = "Hello, World!";
        String expectedOutput1 = "hello world";
        assertEquals(expectedOutput1, ChatbotBrain.preprocessText(input1));

        // Test case 2: Empty input
        String input2 = "";
        String expectedOutput2 = "";
        assertEquals(expectedOutput2, ChatbotBrain.preprocessText(input2));
    }

    @Test
    public void testCosineSimilarity() {
        // Test case 1: Identical strings
        String str1 = "hello world";
        String str2 = "hello world";
        assertEquals(1.0, ChatbotBrain.cosineSimilarity(str1, str2), 0.0001);

        // Test case 2: Different strings
        String str3 = "hello";
        String str4 = "world";
        assertEquals(0.0, ChatbotBrain.cosineSimilarity(str3, str4), 0.0001);
    }

    @Test
    public void testCreateWordVector() {
        // Test case 1: Valid input
        String str1 = "hello world hello";
        String expectedOutput1 = "{world=1, hello=2}";
        assertEquals(expectedOutput1, ChatbotBrain.createWordVector(str1).toString());
    }
}
