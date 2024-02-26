package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Forums extends AppCompatActivity {

    // Variables
    ImageButton back;
    Button post;
    TextView vent, positive, question, userName;

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

        vent.setBackground(getDrawable(R.drawable.grey_tag_background));
        question.setBackground(getDrawable(R.drawable.grey_tag_background));
        positive.setBackground(getDrawable(R.drawable.grey_tag_background));

        Intent myIntent = new Intent(Forums.this, ForumMain.class);

        // When back button is pressed, go to previous screen -> home
        back.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        post.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        vent.setOnClickListener(v -> {
            vent.setBackground(getDrawable(R.drawable.vent_forum_tag));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        });

        question.setOnClickListener(v -> {
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.question_forum_tag));
            positive.setBackground(getDrawable(R.drawable.grey_tag_background));
        });

        positive.setOnClickListener(v -> {
            vent.setBackground(getDrawable(R.drawable.grey_tag_background));
            question.setBackground(getDrawable(R.drawable.grey_tag_background));
            positive.setBackground(getDrawable(R.drawable.positive_forum_tag));
        });

        // Edit user name
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        String key = Login.emailKey;

        if(key == null)
            key = " ";
        userRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null)
                    userName.setText(user.firstName.toUpperCase());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled
            }
        });

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

}

