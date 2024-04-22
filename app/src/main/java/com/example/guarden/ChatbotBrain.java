package com.example.guarden;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

public class ChatbotBrain {
    private static final String[] RESPONSES = {
            "It's completely normal to feel anxious sometimes. When we're faced with challenging situations, our bodies react by releasing stress hormones, which can cause symptoms like increased heart rate, sweating, or racing thoughts. These physical and emotional responses are our body's way of preparing us to deal with threats or dangers. However, if you're feeling overwhelmed by anxiety, it's important to take steps to manage it. This might include practicing relaxation techniques, such as deep breathing or meditation, talking to a therapist or counselor, or finding healthy ways to cope with stress. Remember, you're not alone, and there are people who can help you feel better.",
            "Depression is a serious mental health condition that can affect your thoughts, feelings, and behavior. It's more than just feeling sad or down—it can make it difficult to enjoy life, maintain relationships, or perform daily tasks. Depression can be caused by a combination of genetic, biological, environmental, and psychological factors. It's not something you can just snap out of, and it's not a sign of weakness. If you're experiencing symptoms of depression, such as persistent sadness, loss of interest in activities, changes in appetite or sleep patterns, or feelings of worthlessness or hopelessness, it's important to seek help from a mental health professional. Treatment options may include therapy, medication, lifestyle changes, or a combination of these.",
            "Feeling lonely from time to time is a normal part of life, but chronic loneliness can have serious consequences for your mental and physical health. Loneliness can be caused by various factors, such as social isolation, lack of close relationships, or major life changes like moving to a new city or losing a loved one. If you're feeling lonely, it's important to reach out to others and try to connect with people who share your interests or values. You can also consider joining social groups or volunteering in your community. Remember, it's okay to ask for help when you need it, and there are people who care about you and want to support you.",
            "Stress is a natural response to challenging or threatening situations, but when it becomes overwhelming or chronic, it can have negative effects on your physical and mental health. Chronic stress has been linked to a variety of health problems, including heart disease, obesity, diabetes, and depression. If you're feeling overwhelmed by stress, it's important to take steps to manage it. This might include practicing relaxation techniques like deep breathing or meditation, exercising regularly, getting enough sleep, and setting boundaries to protect your time and energy. Remember, you can't always control the circumstances or events that cause stress, but you can control how you respond to them.",
            "Feeling exhausted or burned out from time to time is normal, especially when you're juggling multiple responsibilities or dealing with challenging situations. However, if you're feeling consistently exhausted or overwhelmed, it's important to take steps to prioritize your mental and physical health. This might include setting boundaries to protect your time and energy, practicing self-care activities like exercise or relaxation techniques, seeking support from friends or family members, and considering professional help if needed. Remember, it's okay to ask for help when you need it, and taking care of yourself is essential for your well-being.",
            "It's natural to feel sad or down from time to time, especially when you're dealing with difficult situations or experiencing losses. However, if you're feeling consistently sad or hopeless, it's important to take steps to address your mental health. This might include reaching out to a therapist or counselor for support, practicing self-care activities like exercise or relaxation techniques, and connecting with supportive friends or family members. Remember, it's okay to ask for help when you need it, and you don't have to go through tough times alone.",
            "Dealing with grief and loss is a natural part of life, but it can be incredibly challenging and overwhelming. Grief can be triggered by various events, such as the death of a loved one, the end of a relationship, or major life changes like moving to a new city or starting a new job. If you're grieving, it's important to give yourself permission to feel and express your emotions, and to seek support from others when you need it. This might include talking to a therapist or counselor, joining a support group, or finding creative outlets for processing your emotions. Remember, everyone grieves in their own way and at their own pace, and it's okay to take as much time as you need to heal.",
            "Feeling overwhelmed by responsibilities or pressures from work, school, or personal life is a common experience, but if it becomes chronic or overwhelming, it can have negative effects on your mental and physical health. Burnout is a state of emotional, physical, and mental exhaustion caused by prolonged stress or pressure, and it can lead to feelings of cynicism, detachment, and decreased effectiveness in your work or personal life. If you're experiencing burnout, it's important to take steps to prioritize your well-being and find healthy ways to cope with stress. This might include setting boundaries, practicing self-care activities like exercise or relaxation techniques, and seeking support from others. Remember, it's okay to ask for help when you need it, and taking care of yourself is essential for your overall well-being.",
            "It's completely normal to feel overwhelmed or stressed out from time to time, especially when you're dealing with challenging situations or major life changes. However, if you're feeling consistently overwhelmed and struggling to cope, it's important to take steps to prioritize your mental and physical health. This might include practicing relaxation techniques like deep breathing or meditation, setting boundaries to protect your time and energy, and seeking support from friends, family, or a mental health professional. Remember, you don't have to go through tough times alone, and there are people who care about you and want to support you.",
            "Feeling anxious or worried from time to time is a normal part of life, but if it becomes overwhelming or interferes with your daily activities, it may be a sign of an anxiety disorder. Anxiety disorders are common mental health conditions that can cause excessive fear, worry, or nervousness, and they can have a significant impact on your quality of life. If you're experiencing symptoms of an anxiety disorder, such as excessive worrying, restlessness, difficulty concentrating, or physical symptoms like rapid heartbeat or sweating, it's important to seek help from a mental health professional. Treatment options may include therapy, medication, lifestyle changes, or a combination of these.",
            "Feeling overwhelmed by stress or pressure from work, school, or personal life is a common experience, but if it becomes chronic or overwhelming, it can have negative effects on your mental and physical health. Burnout is a state of emotional, physical, and mental exhaustion caused by prolonged stress or pressure, and it can lead to feelings of cynicism, detachment, and decreased effectiveness in your work or personal life. If you're experiencing burnout, it's important to take steps to prioritize your well-being and find healthy ways to cope with stress. This might include setting boundaries, practicing self-care activities like exercise or relaxation techniques, and seeking support from others. Remember, it's okay to ask for help when you need it, and taking care of yourself is essential for your overall well-being.",
            "Feeling exhausted or drained from time to time is normal, especially when you're dealing with demanding or stressful situations. However, if you're feeling consistently exhausted and struggling to cope, it's important to take steps to prioritize your mental and physical health. This might include practicing relaxation techniques like deep breathing or meditation, getting regular exercise, getting enough sleep, and setting boundaries to protect your time and energy. Remember, you don't have to go through tough times alone, and there are people who care about you and want to support you.",
            "Feeling sad or down from time to time is a normal part of life, but if it becomes overwhelming or persistent, it may be a sign of depression. Depression is a common mental health condition that can affect your thoughts, feelings, and behavior, and it can have a significant impact on your quality of life. If you're experiencing symptoms of depression, such as persistent sadness, loss of interest in activities, changes in appetite or sleep patterns, or feelings of worthlessness or hopelessness, it's important to seek help from a mental health professional. Treatment options may include therapy, medication, lifestyle changes, or a combination of these.",
            "Feeling lonely or isolated from time to time is a common experience, but if it becomes chronic or overwhelming, it can have negative effects on your mental and physical health. Loneliness can be caused by various factors, such as social isolation, lack of close relationships, or major life changes like moving to a new city or starting a new job. If you're feeling lonely, it's important to take steps to connect with others and build supportive relationships. This might include joining social groups, volunteering in your community, or reaching out to friends or family members for support. Remember, it's okay to ask for help when you need it, and there are people who care about you and want to support you.",
            "Feeling overwhelmed by stress or pressure from work, school, or personal life is a common experience, but if it becomes chronic or overwhelming, it can have negative effects on your mental and physical health. Burnout is a state of emotional, physical, and mental exhaustion caused by prolonged stress or pressure, and it can lead to feelings of cynicism, detachment, and decreased effectiveness in your work or personal life. If you're experiencing burnout, it's important to take steps to prioritize your well-being and find healthy ways to cope with stress. This might include setting boundaries, practicing self-care activities like exercise or relaxation techniques, and seeking support from others. Remember, it's okay to ask for help when you need it, and taking care of yourself is essential for your overall well-being.",
            "Feeling stressed out or overwhelmed from time to time is normal, especially when you're dealing with challenging situations or major life changes. However, if you're feeling consistently stressed and struggling to cope, it's important to take steps to prioritize your mental and physical health. This might include practicing relaxation techniques like deep breathing or meditation, setting boundaries to protect your time and energy, and seeking support from friends, family, or a mental health professional. Remember, you don't have to go through tough times alone, and there are people who care about you and want to support you.",
            "Feeling anxious or worried from time to time is a normal part of life, but if it becomes overwhelming or interferes with your daily activities, it may be a sign of an anxiety disorder. Anxiety disorders are common mental health conditions that can cause excessive fear, worry, or nervousness, and they can have a significant impact on your quality of life. If you're experiencing symptoms of an anxiety disorder, such as excessive worrying, restlessness, difficulty concentrating, or physical symptoms like rapid heartbeat or sweating, it's important to seek help from a mental health professional. Treatment options may include therapy, medication, lifestyle changes, or a combination of these.",
            "Feeling overwhelmed by stress or pressure from work, school, or personal life is a common experience, but if it becomes chronic or overwhelming, it can have negative effects on your mental and physical health. Burnout is a state of emotional, physical, and mental exhaustion caused by prolonged stress or pressure, and it can lead to feelings of cynicism, detachment, and decreased effectiveness in your work or personal life. If you're experiencing burnout, it's important to take steps to prioritize your well-being and find healthy ways to cope with stress. This might include setting boundaries, practicing self-care activities like exercise or relaxation techniques, and seeking support from others. Remember, it's okay to ask for help when you need it, and taking care of yourself is essential for your overall well-being.",
            "Feeling exhausted or drained from time to time is normal, especially when you're dealing with demanding or stressful situations. However, if you're feeling consistently exhausted and struggling to cope, it's important to take steps to prioritize your mental and physical health. This might include practicing relaxation techniques like deep breathing or meditation, getting regular exercise, getting enough sleep, and setting boundaries to protect your time and energy. Remember, you don't have to go through tough times alone, and there are people who care about you and want to support you.",
            "Feeling sad or down from time to time is a normal part of life, but if it becomes overwhelming or persistent, it may be a sign of depression. Depression is a common mental health condition that can affect your thoughts, feelings, and behavior, and it can have a significant impact on your quality of life. If you're experiencing symptoms of depression, such as persistent sadness, loss of interest in activities, changes in appetite or sleep patterns, or feelings of worthlessness or hopelessness, it's important to seek help from a mental health professional. Treatment options may include therapy, medication, lifestyle changes, or a combination of these.",
            "Feeling lonely or isolated from time to time is a common experience, but if it becomes chronic or overwhelming, it can have negative effects on your mental and physical health. Loneliness can be caused by various factors, such as social isolation, lack of close relationships, or major life changes like moving to a new city or starting a new job. If you're feeling lonely, it's important to take steps to connect with others and build supportive relationships. This might include joining social groups, volunteering in your community, or reaching out to friends or family members for support. Remember, it's okay to ask for help when you need it, and there are people who care about you and want to support you.",
    };

    public static String getResponse(String userInput) {
        // Preprocess user input and responses
        userInput = preprocessText(userInput);
        String[] preprocessedResponses = new String[RESPONSES.length];
        for (int i = 0; i < RESPONSES.length; i++) {
            preprocessedResponses[i] = preprocessText(RESPONSES[i]);
        }

        // Calculate cosine similarity between the user input and each response
        double maxSimilarity = -1;
        String bestResponse = "";
        for (int i = 0; i < preprocessedResponses.length; i++) {
            double similarity = cosineSimilarity(userInput, preprocessedResponses[i]);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                bestResponse = RESPONSES[i];
            }
        }

        return bestResponse;
    }

    private static String preprocessText(String text) {
        // Convert text to lowercase and remove punctuation
        text = text.toLowerCase().replaceAll("[^a-zA-Z ]", "");

        // Remove stopwords (optional)
        // Implement a method to remove common stopwords if needed

        return text;
    }

    private static double cosineSimilarity(String str1, String str2) {
        // Calculate cosine similarity between two strings
        Map<String, Integer> vector1 = createWordVector(str1);
        Map<String, Integer> vector2 = createWordVector(str2);

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (Map.Entry<String, Integer> entry : vector1.entrySet()) {
            String word = entry.getKey();
            int count1 = entry.getValue();
            magnitude1 += Math.pow(count1, 2);
            if (vector2.containsKey(word)) {
                dotProduct += count1 * vector2.get(word);
            }
        }

        for (Map.Entry<String, Integer> entry : vector2.entrySet()) {
            int count2 = entry.getValue();
            magnitude2 += Math.pow(count2, 2);
        }

        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);

        if (magnitude1 == 0 || magnitude2 == 0) {
            return 0.0;
        } else {
            return dotProduct / (magnitude1 * magnitude2);
        }
    }

    // Create a word vector for a given string
    private static Map<String, Integer> createWordVector(String str) {
        Map<String, Integer> wordVector = new HashMap<>();
        String[] words = str.split("\\s+");
        for (String word : words) {
            wordVector.put(word, wordVector.getOrDefault(word, 0) + 1);
        }
        return wordVector;
    }
}

