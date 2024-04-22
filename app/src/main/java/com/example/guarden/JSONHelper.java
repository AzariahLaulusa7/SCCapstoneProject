package com.example.guarden;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
//Loads information from therapist JSON file
public class JSONHelper {
    //Gets data from JSON
    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    //Creates therapist array list and adds each therapist as a Therapist object
    public static List<Therapist> loadTherapistsFromJson(Context context, String fileName) {
        String json = loadJSONFromAsset(context, fileName);
        if (json == null) return null;

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Therapist>>() {}.getType();
        return gson.fromJson(json, listType);
    }
}
