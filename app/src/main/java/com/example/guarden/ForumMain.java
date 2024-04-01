package com.example.guarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class ForumMain extends AppCompatActivity {

    // Variables
    ImageButton back, newPost;
    TextView filter, sort, comment1, comment2, comment3;
    EditText commentBox1, commentBox2, commentBox3;
    ImageButton send1, send2, send3;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_main);

        NotificationService.setRecentView("forum_main");
        // Initialize variables
        back = findViewById(R.id.forum_back_icon);
        newPost = findViewById(R.id.new_message);

        sort = findViewById(R.id.sort);
        filter = findViewById(R.id.filter);

        comment1 = findViewById(R.id.comment_one);
        comment2 = findViewById(R.id.comment_two);
        comment3 = findViewById(R.id.comment_three);

        commentBox1 = findViewById(R.id.comment_box_one);
        commentBox2 = findViewById(R.id.comment_box_two);
        commentBox3 = findViewById(R.id.comment_box_three);

        send1 = findViewById(R.id.send_one);
        send2 = findViewById(R.id.send_two);
        send3 = findViewById(R.id.send_three);

        // Hide comment boxes and sends
        commentBox1.setVisibility(View.GONE);
        commentBox2.setVisibility(View.GONE);
        commentBox3.setVisibility(View.GONE);
        send1.setVisibility(View.GONE);
        send2.setVisibility(View.GONE);
        send3.setVisibility(View.GONE);

        Intent myIntent = new Intent(ForumMain.this, HomeScreen.class);
        Intent postPage = new Intent(ForumMain.this, Forums.class);

        // When back button is pressed, go to previous screen -> home
        back.setOnClickListener(v -> {
            startActivity(myIntent);
            finish();
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

        // When a comment button is clicked
        comment1.setOnClickListener(v -> {
            commentBox1.setVisibility(View.VISIBLE);
            send1.setVisibility(View.VISIBLE);
            comment1.setVisibility(View.GONE);
        });

        comment2.setOnClickListener(v -> {
            commentBox2.setVisibility(View.VISIBLE);
            send2.setVisibility(View.VISIBLE);
            comment2.setVisibility(View.GONE);
        });

        comment3.setOnClickListener(v -> {
            commentBox3.setVisibility(View.VISIBLE);
            send3.setVisibility(View.VISIBLE);
            comment3.setVisibility(View.GONE);
        });

        send1.setOnClickListener(v -> {
            commentBox1.setVisibility(View.GONE);
            send1.setVisibility(View.GONE);
            comment1.setVisibility(View.VISIBLE);
        });

        send2.setOnClickListener(v -> {
            commentBox2.setVisibility(View.GONE);
            send2.setVisibility(View.GONE);
            comment2.setVisibility(View.VISIBLE);
        });

        send3.setOnClickListener(v -> {
            commentBox3.setVisibility(View.GONE);
            send3.setVisibility(View.GONE);
            comment3.setVisibility(View.VISIBLE);
        });

    }

}
