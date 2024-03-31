package com.example.guarden;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForumMain extends AppCompatActivity {

    // Variables
    ImageButton back, newPost;
    String name, message, tag;
    TextView filter, sort;
    MyAdapter adapter;
    private DatabaseReference forumRef;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        // Initialize variables
        back = findViewById(R.id.forum_back_icon);
        newPost = findViewById(R.id.new_message);

        sort = findViewById(R.id.sort);
        filter = findViewById(R.id.filter);

        Intent myIntent = new Intent(ForumMain.this, HomeScreen.class);
        Intent postPage = new Intent(ForumMain.this, Forums.class);

        forumRef = FirebaseDatabase.getInstance().getReference().child("forum");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(forumRef, Item.class)
                        .build();

        adapter = new MyAdapter(options);

        recyclerView.setAdapter(adapter);

        // When back button is pressed, go to previous screen -> home
        back.setOnClickListener(v -> {
            startActivity(myIntent);
        });

        // When new post is clicked
        newPost.setOnClickListener(v -> {
            startActivity(postPage);
        });

        // When sort is clicked
        sort.setOnClickListener(v -> {

        });

        // When filter is clicked
        filter.setOnClickListener(v -> {

        });

    }

    @Override
    protected  void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private static class Chat {
        public String name, tag, message;

        public Chat() {}

        public Chat(String name, String tag, String message) {
            this.name = name;
            this.tag = tag;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
