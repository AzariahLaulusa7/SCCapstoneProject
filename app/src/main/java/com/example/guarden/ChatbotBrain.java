package com.example.guarden;

import java.util.ArrayList;
import java.util.Random;

public class ChatbotBrain {
    private ArrayList<String> responses;
    public ChatbotBrain(){

        responses = new ArrayList<String>();
        responses.add("Remember to prioritize self-care by engaging in activities that bring you joy and relaxation, such as taking a warm bath, reading a book, or going for a walk in nature.");
        responses.add("Practice mindfulness and deep breathing exercises to help reduce stress and promote a sense of calmness and clarity in your mind.");
        responses.add("Cultivate a supportive social network by reaching out to friends or loved ones for companionship and emotional support when needed.");
        responses.add("Set achievable goals for yourself and celebrate your accomplishments, no matter how small, to boost your self-esteem and sense of achievement.");
        responses.add("Incorporate regular physical activity into your routine, as exercise has been shown to elevate mood and increase energy levels.");
        responses.add("Challenge negative thoughts and beliefs by reframing them in a more positive and realistic light, fostering a healthier mindset.");
        responses.add("Ensure you're getting adequate sleep each night, aiming for 7-9 hours, as sleep plays a crucial role in regulating mood and restoring energy levels.");
        responses.add("Engage in activities that promote creativity and self-expression, such as painting, writing, or playing music, to nourish your soul and uplift your spirits.");
        responses.add("Practice gratitude by focusing on the things you're thankful for in your life, which can shift your perspective and enhance feelings of happiness and contentment.");
        responses.add("Seek professional support if you're struggling with your mental health, as therapy or counseling can provide valuable guidance and assistance in managing your emotions and improving your overall well-being.");
        responses.add("I am glad you're doing well today! Tell me more!");
        responses.add("That is good to hear!");

    }

    public String query(String prompt){
        //do nothing with prompt for now

        //currently generating a random response
        Random rand = new Random(10);
        int index = rand.nextInt();

        return responses.get(index);
    }

}
