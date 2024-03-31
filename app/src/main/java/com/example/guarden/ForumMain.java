package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.guarden.R;

public class ForumMain extends AppCompatActivity {

    // Variables
    ImageButton back, newPost;
    String name, message, tag;
    TextView filter, sort;
    PostAdapter adapter;
    Intent myIntent;
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

        myIntent = new Intent(ForumMain.this, HomeScreen.class);
        Intent postPage = new Intent(ForumMain.this, Forums.class);

        forumRef = FirebaseDatabase.getInstance().getReference().child("forum");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(forumRef, Post.class)
                .build();

        adapter = new PostAdapter(options);

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
    public  void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adapter.stopListening();
        startActivity(myIntent);
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