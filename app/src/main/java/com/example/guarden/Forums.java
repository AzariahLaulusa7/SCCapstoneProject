package com.example.guarden;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Forums extends AppCompatActivity {

    // Variables
    private StorageReference storageRef;
    private StorageReference imageRef;
    private DatabaseReference forumRef, posRef, ventRef, qRef;
    private DatabaseReference listRef;
    String tagText = "";
    int tagBackground, image;
    EditText postMessage;
    String key;
    ImageButton back;
    Button post;
    ImageView profileImage;
    ImageView image1, image2, image3, image4, image5, image6;
    TextView vent, positive, question, userName;
    Intent myIntent, home;
    SharedPreferences orderPrefs;
    int prefOrderNumber;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);

        // Initialize variables
        back = findViewById(R.id.post_back);
        post = findViewById(R.id.post_button);
        vent = findViewById(R.id.forum_tag_three);
        question = findViewById(R.id.forum_tag_two);
        positive = findViewById(R.id.forum_tag);
        userName = findViewById(R.id.post_user_name);
        postMessage = findViewById(R.id.post_description);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);

        vent.setBackground(getDrawable(R.drawable.grey_tag_background));
        question.setBackground(getDrawable(R.drawable.grey_tag_background));
        positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        image1.setBackground(getDrawable(R.drawable.picked_image_background));
        image = R.drawable.cow;

        orderPrefs = getSharedPreferences("TestWOrderNumber", MODE_PRIVATE);
        prefOrderNumber = orderPrefs.getInt("TestWNUMBER", 10000);

//        SharedPreferences gamePrefs = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
//        int currentBestLevel = gamePrefs.getInt("MemoryGameBestScore", 0);
//        SharedPreferences.Editor editor = gamePrefs.edit();
//        editor.putInt("MemoryGameBestScore", difficultyLevel - 1);
//        editor.apply();

        // Edit user name
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        key = SaveUser.getUserName(Forums.this);

        if(key == null)
            key = " ";
        userRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null && user.firstName != null)
                    userName.setText(user.firstName.toUpperCase()); //Gets name of user associated with a post
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });
        //Gets paths for the sections of the db associated with each forum tag
        posRef = FirebaseDatabase.getInstance().getReference("positiveForum");
        qRef = FirebaseDatabase.getInstance().getReference("questionForum");
        ventRef = FirebaseDatabase.getInstance().getReference("ventForum");
        forumRef = FirebaseDatabase.getInstance().getReference("forum");

        myIntent = new Intent(Forums.this, ForumMain.class);
        home = new Intent(Forums.this, HomeScreen.class);

        // When back button is pressed, go to previous screen -> home
        back.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        //Handles button appearances when vent is pressed
        vent.setOnClickListener(v -> {
            tagText = "vent";
            tagBackground = R.drawable.vent_forum_tag;
            vent.setBackground(getDrawable(R.drawable.vent_forum_tag));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        });

        //Handles button appearances when question is pressed
        question.setOnClickListener(v -> {
            tagText = "question";
            tagBackground = R.drawable.question_forum_tag;
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.question_forum_tag));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        });

        //Handles button appearances when positive is pressed
        positive.setOnClickListener(v -> {
            tagText = "positivity";
            tagBackground = R.drawable.positive_forum_tag;
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.positive_forum_tag));
        });

        //Listeners for each possible profile image
        image1.setOnClickListener(v -> {
            image = R.drawable.cow;
            image1.setBackground(getDrawable(R.drawable.picked_image_background));
            image2.setBackground(getDrawable(R.drawable.profile_border_background));
            image3.setBackground(getDrawable(R.drawable.profile_border_background));
            image4.setBackground(getDrawable(R.drawable.profile_border_background));
            image5.setBackground(getDrawable(R.drawable.profile_border_background));
            image6.setBackground(getDrawable(R.drawable.profile_border_background));
        });

        image2.setOnClickListener(v -> {
            image = R.drawable.wolf;
            image1.setBackground(getDrawable(R.drawable.profile_border_background));
            image2.setBackground(getDrawable(R.drawable.picked_image_background));
            image3.setBackground(getDrawable(R.drawable.profile_border_background));
            image4.setBackground(getDrawable(R.drawable.profile_border_background));
            image5.setBackground(getDrawable(R.drawable.profile_border_background));
            image6.setBackground(getDrawable(R.drawable.profile_border_background));
        });

        image3.setOnClickListener(v -> {
            image = R.drawable.pig;
            image1.setBackground(getDrawable(R.drawable.profile_border_background));
            image2.setBackground(getDrawable(R.drawable.profile_border_background));
            image3.setBackground(getDrawable(R.drawable.picked_image_background));
            image4.setBackground(getDrawable(R.drawable.profile_border_background));
            image5.setBackground(getDrawable(R.drawable.profile_border_background));
            image6.setBackground(getDrawable(R.drawable.profile_border_background));
        });

        image4.setOnClickListener(v -> {
            image = R.drawable.koala;
            image1.setBackground(getDrawable(R.drawable.profile_border_background));
            image2.setBackground(getDrawable(R.drawable.profile_border_background));
            image3.setBackground(getDrawable(R.drawable.profile_border_background));
            image4.setBackground(getDrawable(R.drawable.picked_image_background));
            image5.setBackground(getDrawable(R.drawable.profile_border_background));
            image6.setBackground(getDrawable(R.drawable.profile_border_background));
        });

        image5.setOnClickListener(v -> {
            image = R.drawable.hippo;
            image1.setBackground(getDrawable(R.drawable.profile_border_background));
            image2.setBackground(getDrawable(R.drawable.profile_border_background));
            image3.setBackground(getDrawable(R.drawable.profile_border_background));
            image4.setBackground(getDrawable(R.drawable.profile_border_background));
            image5.setBackground(getDrawable(R.drawable.picked_image_background));
            image6.setBackground(getDrawable(R.drawable.profile_border_background));
        });

        image6.setOnClickListener(v -> {
            image = R.drawable.bear;
            image1.setBackground(getDrawable(R.drawable.profile_border_background));
            image2.setBackground(getDrawable(R.drawable.profile_border_background));
            image3.setBackground(getDrawable(R.drawable.profile_border_background));
            image4.setBackground(getDrawable(R.drawable.profile_border_background));
            image5.setBackground(getDrawable(R.drawable.profile_border_background));
            image6.setBackground(getDrawable(R.drawable.picked_image_background));
        });

        post.setOnClickListener(v -> {
            //Gets user information associated with a new post
            final String name = userName.getText().toString().trim();
            final String tag = tagText.trim();
            final String message = postMessage.getText().toString().trim();
            //final String background = tagBackground.toString().trim();
            //Checks to see if post is empty
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(message)) {
                //Checks if user is logged in
                if (key.equals(" ") || key.isEmpty() || userName.getText().equals("GUEST")) {
                    Toast.makeText(Forums.this, "You NEED an account to post.", Toast.LENGTH_LONG).show();
                } else {
                    if (!TextUtils.isEmpty(tagText) && !TextUtils.isEmpty(message)) { //No tag
                        String uniqueKey = userRef.push().getKey();
                        Chat newChat = new Chat(name, tag, message, image, tagBackground, uniqueKey, SaveUser.getUserName(Forums.this));
                        forumRef.child(uniqueKey).setValue(newChat)
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(Forums.this, "Post Created", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e ->
                                        Toast.makeText(Forums.this, "Failed To Create Post", Toast.LENGTH_SHORT).show());
                        if (tag.equals("positivity")) {
                            posRef.child(uniqueKey).setValue(newChat);
                        } else if (tag.equals("vent")) {
                            ventRef.child(uniqueKey).setValue(newChat);
                        } else {
                            qRef.child(uniqueKey).setValue(newChat);
                        }
                    }
                    startActivity(home);
                    finish();
                }
            }  else {
                Toast.makeText(this, "Please FILL all fields", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(myIntent);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private static class User {
        public String email, password, firstName, lastName, image;

        public User() {}

        public User(String email, String password, String firstName, String lastName) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public User(String email, String password, String firstName, String lastName, String image) {
            this.email = email;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.image = image;
        }
    }

    private static class Chat {
        public String name, tag, message, key, user;
        public int image;
        public int tagBackground, orderNumber;

        public Chat() {}

        public Chat(String name, String tag, String message, int image, int tagBackground, String key, String user) {
            this.name = name;
            this.tag = tag;
            this.message = message;
            this.image = image;
            this.tagBackground = tagBackground;
            this.key = key;
            this.user = user;
        }
    }

}